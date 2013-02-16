package net.stickycode.stereotype.primitive;

import static net.stickycode.exception.Preconditions.notBlank;

import java.io.File;

public class ReadOnlyFile {

  private File readOnlyFile;

  public ReadOnlyFile(String path) {
    this.readOnlyFile = new File(notBlank(path, "Expecting the path to a read only file but got nothing"));

    if (!readOnlyFile.isFile())
      throw new ReadOnlyFileNotFoundException(path);

    if (readOnlyFile.canWrite())
      throw new ReadOnlyFileIsModifiableException(path);
  }

  public File getReadOnlyFile() {
    return readOnlyFile;
  }

}
