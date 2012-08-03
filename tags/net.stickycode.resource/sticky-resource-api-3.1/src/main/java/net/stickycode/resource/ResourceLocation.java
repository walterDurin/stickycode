package net.stickycode.resource;

import net.stickycode.coercion.CoercionTarget;

/**
 * A reference to a resource and its programmatic endpoint, i.e. a path and a fields metadata where it will end up.
 */
public interface ResourceLocation {

  CoercionTarget getResourceTarget();

  String getPath();

  String getScheme();
  
}
