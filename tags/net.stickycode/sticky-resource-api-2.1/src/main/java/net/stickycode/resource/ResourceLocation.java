package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.coercion.CoercionTarget;

public interface ResourceLocation {

  CoercionTarget getResourceTarget();

  InputStream getInputStream();

  OutputStream getOutputStream();

  String getPath();

}
