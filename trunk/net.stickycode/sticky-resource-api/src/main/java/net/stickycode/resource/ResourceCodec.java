package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.coercion.CoercionTarget;


public interface ResourceCodec<T> {

  T load(InputStream source);

  void store(T resource, OutputStream target);

  boolean isApplicableTo(CoercionTarget type);

}
