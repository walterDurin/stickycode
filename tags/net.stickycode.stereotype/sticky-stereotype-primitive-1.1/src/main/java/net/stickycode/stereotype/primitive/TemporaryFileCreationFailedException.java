package net.stickycode.stereotype.primitive;

import java.io.IOException;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class TemporaryFileCreationFailedException
    extends TransientException {

  public TemporaryFileCreationFailedException(IOException e, String pathname) {
    super(e, "Failed to create temporary file using template ''", pathname);
  }

}
