package net.stickycode.resource;

import java.util.Set;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceCodecNotFoundException
    extends PermanentException {

  public ResourceCodecNotFoundException(CoercionTarget type, Set<ResourceCodec> codecs) {
    super("Codec for {} not found in codecs {}", type, codecs);
  }

}
