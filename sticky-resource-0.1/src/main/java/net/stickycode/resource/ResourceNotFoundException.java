package net.stickycode.resource;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceNotFoundException
    extends PermanentException {

  public ResourceNotFoundException(Resource resource) {
    super("Resource not found at '{}'", resource.toString());
  }

  public ResourceNotFoundException(Throwable e, Resource resource) {
    super(e, "Resource not found at '{}'", resource.toString());
  }
}
