package net.stickycode.resource;

import net.stickycode.coercion.CoercionTarget;

public interface ResourceCodecFactory<T> {

  boolean isApplicableTo(CoercionTarget type);

  ResourceCodec<T> create(CoercionTarget type);
}
