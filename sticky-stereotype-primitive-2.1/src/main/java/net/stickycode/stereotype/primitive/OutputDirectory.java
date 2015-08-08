package net.stickycode.stereotype.primitive;

import static net.stickycode.exception.Preconditions.notBlank;

import java.io.File;

public class OutputDirectory {

  private File directory;

  public OutputDirectory(String path) {
    this.directory = new File(notBlank(path, "Expected a path for an output directory but got null"));

    if (!directory.exists())
      if (!directory.mkdirs())
        throw new OutputDirectoryCannotBeCreatedException(path);

    if (!directory.isDirectory())
      throw new OutputDirectoryCannotBeAFileException(path);

    if (!directory.canWrite())
      throw new OutputDirectoryIsNotModifiableException(path);
  }

  public File getDirectory() {
    return directory;
  }

}
