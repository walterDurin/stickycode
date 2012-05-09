package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.coercion.CoercionTarget;

/**
 * Contract for algorithm's that can encode and decode streams into particular targets, where a target is a field or other type.
 */
public interface ResourceCodec<T> {

  T load(InputStream source, CoercionTarget targetType);

  void store(CoercionTarget sourceType, T resource, OutputStream target);

  boolean isApplicableTo(CoercionTarget type);

}
