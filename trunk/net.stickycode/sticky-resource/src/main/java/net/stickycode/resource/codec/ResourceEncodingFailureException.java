package net.stickycode.resource.codec;

import javax.xml.bind.JAXBException;

import net.stickycode.exception.TransientException;
import net.stickycode.resource.ResourceCodec;


@SuppressWarnings("serial")
public class ResourceEncodingFailureException
    extends TransientException {

  public ResourceEncodingFailureException(JAXBException e, Class<?> type, ResourceCodec<?> codec) {
    super(e, "Failed to encode {} using {}", type, codec);
  }

}
