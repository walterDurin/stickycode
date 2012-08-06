package net.stickycode.resource;

import java.io.Closeable;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;

public interface ResourceInput
    extends Closeable {

  <T> T load(CoercionTarget resourceTarget, ResourceCodec<T> codec, Charset characterSet);

}
