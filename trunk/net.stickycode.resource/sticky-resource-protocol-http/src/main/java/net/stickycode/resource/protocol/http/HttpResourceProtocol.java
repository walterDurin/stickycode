package net.stickycode.resource.protocol.http;

import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;

public class HttpResourceProtocol
    implements ResourceProtocol {

  @Override
  public InputStream getInputStream(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return null;
  }

  @Override
  public boolean canResolve(String protocol) {
    return protocol.startsWith("http://");
  }

  @Override
  public OutputStream getOutputStream(ResourceLocation uriResourceLocation) {
    return null;
  }

}
