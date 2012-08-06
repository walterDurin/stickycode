package net.stickycode.resource.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathResourceInput
    implements ResourceInput {

  private Logger log = LoggerFactory.getLogger(getClass());

  private InputStream stream;

  public ClasspathResourceInput(ResourceLocation location) {
    stream = buildStream(location);
  }

  private InputStream buildStream(ResourceLocation location) {
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
  public void close()
      throws IOException {
    stream.close();
  }
  
  @Override
  public <T> T load(CoercionTarget resourceTarget, ResourceCodec<T> codec, Charset characterSet) {
    return codec.load(resourceTarget, stream, characterSet);
  }


}
