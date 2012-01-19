package net.stickycode.coercion.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceSpecification;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class DummyProtocol
    implements ResourceProtocol {

  @Override
  public InputStream getInputStream(ResourceSpecification resource) throws ResourceNotFoundException {
    try {
      return new ByteArrayInputStream("hmm".getBytes("UTF-8"));
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean canResolve(String protocol) {
    return "dummy".equals(protocol);
  }

}
