package net.stickycode.stereotype.primitive;

import java.io.File;
import java.io.IOException;

public class TemporaryFile {

  private File temporaryFile;

  public TemporaryFile(String pathname) {
    try {
      int indexOfPeriod = pathname.lastIndexOf('.');
      if (indexOfPeriod == -1)
        this.temporaryFile = create(pathname, null);
      else
        this.temporaryFile = create(pathname.substring(0, indexOfPeriod), pathname.substring(indexOfPeriod));

    }
    catch (IOException e) {
      throw new TemporaryFileCreationFailedException(e, pathname);
    }
  }

  File create(String prefix, String suffix)
      throws IOException {
    return File.createTempFile(prefix, suffix);
  }

  public File getTemporaryFile() {
    return temporaryFile;
  }

}
