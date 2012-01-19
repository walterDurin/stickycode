package net.stickycode.resource;

import java.io.InputStream;

public interface ResourceProtocol {

  InputStream getInputStream(ResourceSpecification resource)
      throws ResourceNotFoundException;

  boolean canResolve(String protocol);

}
