package net.stickycode.stereotype.primitive;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ReadOnlyFileNotFoundException
    extends TransientException {

  public ReadOnlyFileNotFoundException(String path) {
    super("Path '' was not found, expecting a read only file", path);
  }

}
