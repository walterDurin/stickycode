package net.stickycode.resource.codec;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;

public class JaxbElementResourceCodec
    implements ResourceCodec<Object> {

  private JAXBContext context;

  private Class<?> type;

  public JaxbElementResourceCodec(Class<?> type) {
    this.type = type;
    try {
      this.context = JAXBContext.newInstance(type);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void store(Object resource, OutputStream outputStream) {
    try {
      QName name = new QName("ns://" + type.getPackage().getName(), type.getSimpleName());
      JAXBElement element = new JAXBElement(name, type, resource);
      Marshaller m = context.createMarshaller();
      m.marshal(element, outputStream);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object load(InputStream source) {
    try {
      Unmarshaller u = context.createUnmarshaller();
      return u.unmarshal(new StreamSource(source), type);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(JAXBElement.class);
  }

}
