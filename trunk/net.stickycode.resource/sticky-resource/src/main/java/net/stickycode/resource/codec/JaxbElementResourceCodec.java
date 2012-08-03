package net.stickycode.resource.codec;

import java.beans.Introspector;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceConnection;
import net.stickycode.stereotype.plugin.StickyExtension;
import net.stickycode.xml.jaxb.JaxbFactory;

@StickyExtension
public class JaxbElementResourceCodec<T>
    implements ResourceCodec<T> {

  @Inject
  private JaxbFactory jaxbFactory;

  @Override
  public void store(CoercionTarget sourceType, T resource, ResourceConnection connection) {
    Class<?> type = sourceType.getType();
    XmlType annotation = type.getAnnotation(XmlType.class);
    try {
      assert annotation != null;

      QName name = new QName(namespace(annotation), typeName(annotation, type));

      @SuppressWarnings({ "unchecked", "rawtypes" })
      JAXBElement element = new JAXBElement(name, type, resource);
      Marshaller m = jaxbFactory.createMarshaller(sourceType);
      m.marshal(element, connection.getOutputStream());
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
  public T load(ResourceConnection connection, CoercionTarget resourceTarget) {
    @SuppressWarnings("unchecked")
    Class<T> type = (Class<T>) resourceTarget.getType();
    try {
      Unmarshaller u = jaxbFactory.createUnmarshaller(resourceTarget);
      return u.unmarshal(new StreamSource(connection.getInputStream()), type).getValue();
    }
    catch (JAXBException e) {
      throw new ResourceDecodingFailureException(e, type, this);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    if (type.getType().isAnnotationPresent(XmlType.class))
      return true;

    if (type.getType().isAssignableFrom(JAXBElement.class))
      return true;

    return false;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  @Override
  public String getDefaultFileSuffix() {
    return ".xml";
  }

}
