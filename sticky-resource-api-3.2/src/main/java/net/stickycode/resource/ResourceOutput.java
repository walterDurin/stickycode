package net.stickycode.resource;

import java.io.Closeable;

import net.stickycode.coercion.CoercionTarget;

public interface ResourceOutput
    extends Closeable {

  <T> void store(T value, CoercionTarget type, ResourceCodec<T> codec);

}
