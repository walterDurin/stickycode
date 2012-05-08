package net.stickycode.resource;

import net.stickycode.coercion.CoercionTarget;

public interface ResourceCodecRegistry {

  <T> ResourceCodec<T> find(CoercionTarget coercionTarget)
      throws ResourceCodecNotFoundException;

}
