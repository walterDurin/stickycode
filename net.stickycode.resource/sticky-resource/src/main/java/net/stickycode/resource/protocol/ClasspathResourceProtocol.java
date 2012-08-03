package net.stickycode.resource.protocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.stickycode.resource.ResourceConnection;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class ClasspathResourceProtocol
    implements ResourceProtocol {

  private Logger log = LoggerFactory.getLogger(getClass());

  private class ClasspathResourceConnection
      implements ResourceConnection {

    private ResourceLocation location;

    public ClasspathResourceConnection(ResourceLocation resource) {
      super();
      this.location = resource;
    }

    @Override
    public InputStream getInputStream() {
      Class<?> base = location.getResourceTarget().getOwner();

      String path = location.getPath();
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
    public OutputStream getOutputStream() {
      // TODO should this check if the classpath is a filesystem, and if so allow updates
      throw new UnsupportedOperationException("Classpath resources cannot be written");
    }

    @Override
    public Charset getCharacterSet() {
      return Charset.forName("UTF-8");
    }

    public ResourceLocation getLocation() {
      return location;
    }

    @Override
    public void store(Object content) {
    }

  }

  @Override
  public boolean canResolve(String protocol) {
    return "classpath".equals(protocol);
  }

  @Override
  public ResourceConnection createConnection(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return new ClasspathResourceConnection(resourceLocation);
  }

}
