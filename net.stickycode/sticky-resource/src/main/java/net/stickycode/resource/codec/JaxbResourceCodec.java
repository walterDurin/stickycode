package net.stickycode.resource.codec;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.stereotype.component.StickyExtension;
import net.stickycode.xml.jaxb.JaxbFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class JaxbResourceCodec<T>
    implements ResourceCodec<T> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private JaxbFactory jaxbFactory;

  @Override
  public void store(CoercionTarget sourceType, T resource, OutputStream outputStream) {
    try {
      log.debug("storing {} of type {}", resource, sourceType);
      Marshaller m = jaxbFactory.createMarshaller(sourceType);
      m.marshal(resource, outputStream);
    }
    catch (JAXBException e) {
      throw new ResourceEncodingFailureException(e, sourceType, this);
    }
  }

  @Override
  public T load(InputStream source, CoercionTarget resourceTarget) {
    log.debug("loading {} from {}", resourceTarget);
    @SuppressWarnings("unchecked")
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
    return type.getType().isAnnotationPresent(XmlRootElement.class);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
