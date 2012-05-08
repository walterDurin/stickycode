package net.stickycode.resource;

import java.util.Set;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceCodecNotFoundException
    extends PermanentException {

  @SuppressWarnings("rawtypes")
  public ResourceCodecNotFoundException(CoercionTarget target, Set<ResourceCodec> codecs) {
    super("Failed to find a resource codec handler for {} in {}", target, codecs);
  }

}
