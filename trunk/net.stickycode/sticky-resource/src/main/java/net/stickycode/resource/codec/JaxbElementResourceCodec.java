package net.stickycode.resource.codec;

import java.beans.Introspector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class JaxbElementResourceCodec<T>
    implements ResourceCodec<T> {
  
  private final class StrictErrorHandler
      implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
      throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
      throw exception;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
      throw exception;
    }
  }

  private Logger log = LoggerFactory.getLogger(getClass());

  private JAXBContext context;

  private Schema schema;

  private Class<T> type;

  public JaxbElementResourceCodec(Class<T> type) {
    this.type = type;
    try {
      this.context = JAXBContext.newInstance(type);
      generateSchema();
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateSchema() throws IOException, SAXException {
    ByteArrayOutputStream element = new ByteArrayOutputStream();
    element.write(elementSchema());

    LocalResolver resolver = new LocalResolver();
    context.generateSchema(resolver);

    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setErrorHandler(new StrictErrorHandler());
    sf.setResourceResolver(resolver);
    this.schema = sf.newSchema(new StreamSource(new ByteArrayInputStream(element.toByteArray()), "internal"));
  }

  private byte[] elementSchema() throws UnsupportedEncodingException {
    String namespace = namespace();
    return ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n\n"
        + "  <xs:include schemaLocation=\"schema1.xsd\" />\n"
        + "  <xs:element name=\"bean\" type=\"bean\" />\n"
        + "</xs:schema>").getBytes("UTF-8");
  }

  private String namespace() {
    return namespace(type.getAnnotation(XmlType.class));
  }

  @Override
  public void store(T resource, OutputStream outputStream) {
    try {
      XmlType annotation = type.getAnnotation(XmlType.class);
      assert annotation != null;

      QName name = new QName(namespace(annotation), typeName(annotation));
      
      @SuppressWarnings({ "unchecked", "rawtypes" })
      JAXBElement element = new JAXBElement(name, type, resource);
      Marshaller m = context.createMarshaller();
      m.setSchema(schema);
      m.marshal(element, outputStream);
    }
    catch (JAXBException e) {
      throw new ResourceEncodingFailureException(e, type, this);
    }
  }

  private String typeName(XmlType annotation) {
    if (annotation.name().equals("##default"))
      return Introspector.decapitalize(type.getSimpleName());

    return annotation.name();
  }

  /**
   * TODO This is not sufficient it needs to check package annotations etc as well  
   */
  private String namespace(XmlType annotation) {
    if (annotation.namespace().equals("##default"))
      return "";

    return annotation.namespace();
  }

  @Override
  public T load(InputStream source) {
    try {
      Unmarshaller u = context.createUnmarshaller();
      u.setSchema(schema);
      return u.unmarshal(new StreamSource(source), type).getValue();
    }
    catch (JAXBException e) {
      throw new ResourceDecodingFailureException(e, type, this);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAnnotationPresent(XmlType.class);
  }
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + type.getSimpleName() + "]";
  }

}
