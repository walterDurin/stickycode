package net.stickycode.stereotype.primitive;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class OutputDirectoryIsNotModifiableException
    extends PermanentException {

  public OutputDirectoryIsNotModifiableException(String path) {
    super("The directory denoted by path '' is not modifiable but needs to be", path);
  }

}
