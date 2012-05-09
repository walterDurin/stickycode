package net.stickycode.resource.codec;

import net.stickycode.exception.TransientException;
import net.stickycode.resource.ResourceCodec;

@SuppressWarnings("serial")
public class ResourceDecodingFailureException
    extends TransientException {

  public ResourceDecodingFailureException(Throwable e, Class<?> type, ResourceCodec<?> codec) {
    super(e, "Failed to decode to {} using {}", type.getSimpleName(), codec);
  }

}
