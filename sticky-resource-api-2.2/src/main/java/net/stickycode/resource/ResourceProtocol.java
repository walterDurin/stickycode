package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The contract for resolving in and outbound streams for resources. A representation of the wire protocol for them.
 */
public interface ResourceProtocol {

  InputStream getInputStream(ResourceLocation resourceLocation)
      throws ResourceNotFoundException;

  boolean canResolve(String protocol);

  OutputStream getOutputStream(ResourceLocation uriResourceLocation);

}
