package net.stickycode.resource;

import static net.stickycode.exception.Preconditions.notNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClasspathResource
    implements ResourceReference {

  @SuppressWarnings("rawtypes")
  private final Class base;

  private final String name;

  public ClasspathResource(Class<?> base, String name) {
    this.base = notNull(base, "Base class cannot be null");
    this.name = notNull(name, "Resource name cannot be null");
  }

  @Override
  public boolean canLoad() {
    return base.getResource(name) != null;
  }

  @Override
  public InputStream getSource() {
    InputStream resourceAsStream = resolveInputStream();
    if (resourceAsStream == null)
      throw new ResourceNotFoundException(this);

    return resourceAsStream;
  }

  private InputStream resolveInputStream() {
    return base.getResourceAsStream(name);
  }

  @Override
  public String toString() {
    if (name.startsWith("/"))
      return "classpath://" + name;

    return "classpath://" + base.getPackage().getName().replace('.', '/') + "/" + name;
  }

  public File toFile() {
    throw new UnsupportedOperationException("Resource " + toString() + " cannot be converted to a file");
  }

  @Override
  public RuntimeException decodeException(IOException e) {
     throw new FailedToLoadResourceException(e, this);
  }
}
