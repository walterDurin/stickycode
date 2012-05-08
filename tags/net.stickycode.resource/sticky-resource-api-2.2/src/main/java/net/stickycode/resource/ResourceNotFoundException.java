package net.stickycode.resource;

import java.io.FileNotFoundException;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ResourceNotFoundException
    extends TransientException {

  public ResourceNotFoundException(String path) {
    super("Could not find resource at {}", path);
  }

  public ResourceNotFoundException(FileNotFoundException e, String path) {
    super(e, "Could not find resource at {}", path);
  }

}
