package net.stickycode.resource;

import java.io.InputStream;

import net.stickycode.coercion.CoercionTarget;


public interface ResourceLocation {

  CoercionTarget getResourceTarget();

  InputStream getInputStream();

  String getPath();


}
