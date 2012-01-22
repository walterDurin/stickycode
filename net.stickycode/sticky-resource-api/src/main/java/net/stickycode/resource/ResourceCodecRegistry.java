package net.stickycode.resource;

import net.stickycode.coercion.CoercionTarget;



public interface ResourceCodecRegistry {

  ResourceCodec<?> find(CoercionTarget coercionTarget);

}
