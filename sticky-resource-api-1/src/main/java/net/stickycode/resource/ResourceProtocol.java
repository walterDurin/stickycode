package net.stickycode.resource;

import java.io.InputStream;

public interface ResourceProtocol {

  InputStream getInputStream(ResourceLocation resourceLocation)
      throws ResourceNotFoundException;

  boolean canResolve(String protocol);

}
