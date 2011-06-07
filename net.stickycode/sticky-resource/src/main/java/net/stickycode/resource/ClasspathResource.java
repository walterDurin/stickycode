package net.stickycode.resource;

import static net.stickycode.exception.Preconditions.notNull;

import java.io.File;
import java.io.InputStream;

public class ClasspathResource
    implements Resource {

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
    InputStream resourceAsStream = base.getResourceAsStream(name);
    if (resourceAsStream == null)
      throw new ResourceNotFoundException(this);

    return resourceAsStream;
  }

  @Override
  public String toString() {
    return "classpath://" + base.getPackage().getName().replace('.', '/') + "/" + name;
  }

  public File toFile() {
    throw new UnsupportedOperationException("Resource " + toString() + " cannot be converted to a file");
  }
}
