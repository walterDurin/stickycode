package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.coercion.CoercionTarget;

/**
 * A reference to a resource and its programmatic endpoint, i.e. a path and a fields metadata where it will end up.
 */
public interface ResourceLocation {

  CoercionTarget getResourceTarget();

  InputStream getInputStream();

  OutputStream getOutputStream();

  String getPath();

}
