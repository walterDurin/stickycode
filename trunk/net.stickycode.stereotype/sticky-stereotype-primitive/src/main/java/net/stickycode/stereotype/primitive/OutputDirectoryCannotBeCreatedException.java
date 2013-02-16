package net.stickycode.stereotype.primitive;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class OutputDirectoryCannotBeCreatedException
    extends PermanentException {

  public OutputDirectoryCannotBeCreatedException(String path) {
    super("Failed to create a directory denoted by path ''", path);
  }

}
