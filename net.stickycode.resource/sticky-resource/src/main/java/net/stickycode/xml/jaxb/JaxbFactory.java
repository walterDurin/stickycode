package net.stickycode.xml.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class JaxbFactory {

  private Map<Class<?>, JAXBContext> contexts = new WeakHashMap<Class<?>, JAXBContext>();

  private Map<JAXBContext, Schema> schema = new WeakHashMap<JAXBContext, Schema>();

  public Unmarshaller createUnmarshaller(CoercionTarget resourceTarget) {
    Class<?> type = resourceTarget.getType();
    JAXBContext context = getContext(type);
    Unmarshaller unmarshaller = createUnmarshaller(context);

    unmarshaller.setSchema(getSchema(context, type));

    return unmarshaller;
  }

  public Marshaller createMarshaller(CoercionTarget target) {
    Class<?> type = target.getType();
    JAXBContext context = getContext(type);
    Marshaller marshaller = createMarshaller(context);

    marshaller.setSchema(getSchema(context, type));

    return marshaller;
  }

  private Schema getSchema(JAXBContext context, Class<?> type) {
    if (schema.containsKey(context))
      return schema.get(context);

    synchronized (schema) {
      if (!schema.containsKey(context))
        schema.put(context, generateSchema(context, type));

      return schema.get(context);
    }
  }

  private Schema generateSchema(JAXBContext context, Class<?> type) {
    LocalResolver resolver = new LocalResolver();
    generateSchemaFromContext(context, resolver);

    resolver.postProcess();
    
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setErrorHandler(new StrictErrorHandler());
    sf.setResourceResolver(resolver);
    return createSchema(sf, type, resolver);
  }

  private Schema createSchema(SchemaFactory sf, Class<?> type, LocalResolver resolver) {
    try {
      return sf.newSchema(createSchemaSources(type, resolver));
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateSchemaFromContext(JAXBContext context, LocalResolver resolver) {
    try {
      context.generateSchema(resolver);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private StreamSource[] createSchemaSources(Class<?> type, LocalResolver resolver) {
    if (type.isAnnotationPresent(XmlRootElement.class))
      return resolver.getSources();
    
    ByteArrayOutputStream element = new ByteArrayOutputStream();
    try {
      element.write(elementSchema());
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new StreamSource[] {new StreamSource(new ByteArrayInputStream(element.toByteArray()), "internal")};
  }

  private byte[] elementSchema() throws UnsupportedEncodingException {
    return ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n\n"
        + "  <xs:include schemaLocation=\"schema1.xsd\" />\n"
        + "  <xs:element name=\"bean\" type=\"bean\" />\n"
        + "</xs:schema>").getBytes("UTF-8");
  }

  private Unmarshaller createUnmarshaller(JAXBContext context) {
    try {
      return context.createUnmarshaller();
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private Marshaller createMarshaller(JAXBContext context) {
    try {
      return context.createMarshaller();
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private JAXBContext getContext(Class<?> type) {
    if (contexts.containsKey(type))
      return contexts.get(type);

    try {
      synchronized (contexts) {
        if (!contexts.containsKey(type))
          contexts.put(type, JAXBContext.newInstance(type));

        return contexts.get(type);
      }
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

}
