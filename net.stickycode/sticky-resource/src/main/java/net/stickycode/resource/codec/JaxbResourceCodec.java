package net.stickycode.resource.codec;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import net.stickycode.resource.ResourceCodec;

public class JaxbResourceCodec
    implements ResourceCodec<Object> {

  private JAXBContext context;

  private Class<?> type;

  public JaxbResourceCodec(Class<?> type) {
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
  }

  @Override
  public Object load(InputStream source) {
    try {
      Unmarshaller u = context.createUnmarshaller();
      return u.unmarshal(new StreamSource(source), type).getValue();
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

}
