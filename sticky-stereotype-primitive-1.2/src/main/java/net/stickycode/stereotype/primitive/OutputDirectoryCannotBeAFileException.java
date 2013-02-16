package net.stickycode.stereotype.primitive;

import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class OutputDirectoryCannotBeAFileException
    extends PermanentException {

  public OutputDirectoryCannotBeAFileException(String path) {
    super("The path '' is a file when a directory was expected", path);
  }

}
