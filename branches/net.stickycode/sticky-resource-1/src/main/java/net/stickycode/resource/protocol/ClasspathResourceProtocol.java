package net.stickycode.resource.protocol;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class ClasspathResourceProtocol
    implements ResourceProtocol {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public InputStream getInputStream(ResourceLocation resource) {
    Class<?> base = resource.getResourceTarget().getOwner();

    String path = resource.getPath();
    log.debug("loading resource {}", path);
    InputStream stream = base.getResourceAsStream(path);
    if (stream == null)
      throw new ResourceNotFoundException(createPath(path, base));

    return stream;
  }

  private String createPath(String path, Class<?> base) {
    if (path.startsWith("/"))
      return "classpath://" + path;

    return "classpath://" + base.getPackage().getName().replace('.', '/') + "/" + path;
  }

  @Override
  public boolean canResolve(String protocol) {
    return "classpath".equals(protocol);
  }

}
