package net.stickycode.resource;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ResourceNotFoundException
    extends TransientException {

  public ResourceNotFoundException(String path) {
    super("Could not find resource at {}", path);
  }

}
