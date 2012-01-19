package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;


public interface ResourceCodec<T> {

  T load(InputStream source);

  void store(T resource, OutputStream target);

}
