package net.stickycode.resource.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;

public class FileResourceProtocol
    implements ResourceProtocol {

  @Override
  public InputStream getInputStream(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    String path = resolvePath(resourceLocation);
    File file = new File(path);
    if (!file.canRead())
      throw new ResourceNotFoundException(path);

    try {
      return new FileInputStream(file);
    }
    catch (FileNotFoundException e) {
      throw new ResourceNotFoundException(e, path);
    }
  }

  private String resolvePath(ResourceLocation resourceLocation) {
    return resourceLocation.getPath().substring("file://".length());
  }

  @Override
  public boolean canResolve(String protocol) {
    return protocol.startsWith("file://");
  }

  @Override
  public OutputStream getOutputStream(ResourceLocation resourceLocation) {
    String path = resolvePath(resourceLocation);
    File file = new File(path);
    try {
      return new FileOutputStream(file);
    }
    catch (FileNotFoundException e) {
      if (file.exists())
        throw new ResourceAuthorisationFailureException(resourceLocation);

      if (file.getParentFile().isDirectory())
        throw new ResourceAuthorisationFailureException(resourceLocation);

      throw new ResourcePathNotFoundForWriteException(e, resourceLocation);
    }
  }

}
