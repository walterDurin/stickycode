package net.stickycode.resource.protocol;

import java.io.InputStream;

import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceSpecification;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class ClasspathResourceProtocol
    implements ResourceProtocol {

  @Override
  public InputStream getInputStream(ResourceSpecification resource) {
    assert resource.getContext() != null;
    
    InputStream stream = resource.getContext().getResourceAsStream(resource.getPath());
    if (stream == null)
      throw new ResourceNotFoundException(createPath(resource));

    return stream;
  }

  private String createPath(ResourceSpecification resource) {
    String path = resource.getPath();
    if (path.startsWith("/"))
      return "classpath://" + path;

    return "classpath://" + resource.getContext().getPackage().getName().replace('.', '/') + "/" + path;
  }

  @Override
  public boolean canResolve(String protocol) {
    return "classpath".equals(protocol);
  }

}
