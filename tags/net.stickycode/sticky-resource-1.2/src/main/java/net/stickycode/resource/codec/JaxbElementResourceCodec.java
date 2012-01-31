package net.stickycode.resource.codec;

import java.beans.Introspector;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.stereotype.component.StickyExtension;
import net.stickycode.xml.jaxb.JaxbFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class JaxbElementResourceCodec<T>
    implements ResourceCodec<T> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
    private JaxbFactory jaxbFactory;

//  private void generateSchema() throws IOException, SAXException {
//    ByteArrayOutputStream element = new ByteArrayOutputStream();
//    element.write(elementSchema());
//
//    LocalResolver resolver = new LocalResolver();
//    context.generateSchema(resolver);
//
//    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//    sf.setErrorHandler(new StrictErrorHandler<T>());
//    sf.setResourceResolver(resolver);
//    this.schema = sf.newSchema(new StreamSource(new ByteArrayInputStream(element.toByteArray()), "internal"));
//  }
//
//  private byte[] elementSchema() throws UnsupportedEncodingException {
//    return ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
//        + "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n\n"
//        + "  <xs:include schemaLocation=\"schema1.xsd\" />\n"
//        + "  <xs:element name=\"bean\" type=\"bean\" />\n"
//        + "</xs:schema>").getBytes("UTF-8");
//  }
//
//  private String namespace() {
//    return namespace(type.getAnnotation(XmlType.class));
//  }

  @Override
  public void store(CoercionTarget sourceType, T resource, OutputStream outputStream) {
    Class<?> type = sourceType.getType();
    XmlType annotation = type.getAnnotation(XmlType.class);
    try {
      assert annotation != null;

      QName name = new QName(namespace(annotation), typeName(annotation, type));

      @SuppressWarnings({ "unchecked", "rawtypes" })
      JAXBElement element = new JAXBElement(name, type, resource);
      Marshaller m = jaxbFactory.createMarshaller(sourceType);
      m.marshal(element, outputStream);
    }
    catch (JAXBException e) {
      throw new ResourceEncodingFailureException(e, type, this);
    }
  }

  private String typeName(XmlType annotation, Class<?> type) {
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
  public T load(InputStream source, CoercionTarget resourceTarget) {
    Class<T> type = (Class<T>) resourceTarget.getType();
    try {
      Unmarshaller u = jaxbFactory.createUnmarshaller(resourceTarget);
      return u.unmarshal(new StreamSource(source), type).getValue();
    }
    catch (JAXBException e) {
      throw new ResourceDecodingFailureException(e, type, this);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    if (type.getType().isAnnotationPresent(XmlType.class))
      return true;

    return false;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
