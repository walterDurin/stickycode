package net.stickycode.resource;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceNotFoundException
    extends PermanentException {

  public ResourceNotFoundException(ResourceReference resource) {
    super("Resource not found at '{}'", resource.toString());
  }

  public ResourceNotFoundException(Throwable e, ResourceReference resource) {
    super(e, "Resource not found at '{}'", resource.toString());
  }
}
