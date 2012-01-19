package net.stickycode.resource.directory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.stickycode.resource.FailedToLoadResourceException;
import net.stickycode.resource.ResourceReference;
import net.stickycode.resource.ResourceNotFoundException;

public class FileResource
    implements ResourceReference {

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
    return "file://" + file.getPath();
  }

  @Override
  public InputStream getSource() {
    if (file.canRead())
      try {
        return new BufferedInputStream(new FileInputStream(file));
      }
      catch (FileNotFoundException e) {
        throw new ResourceNotFoundException(e, this);
      }

    File parentFile = file.getParentFile();
    while (parentFile != null) {
      if (parentFile.canRead())
        throw new ResourceAccessDeniedException(file, parentFile);

      parentFile = parentFile.getParentFile();
    }

    throw new ResourceAccessDeniedException(file);
  }

  @Override
  public File toFile() {
    return file;
  }

  @Override
  public RuntimeException decodeException(IOException e) {
    if (e instanceof FileNotFoundException)
      return new ResourceNotFoundException(e, this);

    return new FailedToLoadResourceException(e, this);
  }

}
