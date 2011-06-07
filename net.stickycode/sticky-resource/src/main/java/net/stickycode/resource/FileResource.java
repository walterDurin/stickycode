package net.stickycode.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileResource
    implements Resource {

  private final File file;

  public FileResource(File file) {
    this.file = file;
  }

  @Override
  public boolean canLoad() {
    return file.canRead();
  }

  @Override
  public String toString() {
    return "file://" + file.getAbsolutePath();
  }

  @Override
  public InputStream getSource() {
    try {
      return new BufferedInputStream(new FileInputStream(file));
    }
    catch (FileNotFoundException e) {
      throw new ResourceNotFoundException(e, this);
    }
  }

  @Override
  public File toFile() {
    return file;
  }

}
