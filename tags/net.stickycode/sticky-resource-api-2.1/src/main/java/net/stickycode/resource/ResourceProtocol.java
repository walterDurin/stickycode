package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

public interface ResourceProtocol {

  InputStream getInputStream(ResourceLocation resourceLocation)
      throws ResourceNotFoundException;

  boolean canResolve(String protocol);

  OutputStream getOutputStream(ResourceLocation uriResourceLocation);

}
