package net.stickycode.resource.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.stickycode.resource.ResourceAuthorisationFailureException;
import net.stickycode.resource.ResourceConnection;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class FileResourceProtocol
    implements ResourceProtocol {

  @Override
  public ResourceConnection createConnection(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return new FileResourceConnection(resourceLocation);
  }

  @Override
  public boolean canResolve(String protocol) {
    return protocol.startsWith("file");
  }

  private class FileResourceConnection
      implements ResourceConnection {

    private ResourceLocation location;

    

    public FileResourceConnection(ResourceLocation resourceLocation) {
      this.location = resourceLocation;
    }

    @Override
    public InputStream getInputStream()
        throws ResourceNotFoundException {
      String path = resolvePath();
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

    private String resolvePath() {
      return location.getPath();
    }

    @Override
    public OutputStream getOutputStream() {
      String path = resolvePath();
      File file = new File(path);
      try {
        return new FileOutputStream(file);
      }
      catch (FileNotFoundException e) {
        if (file.exists())
          throw new ResourceAuthorisationFailureException(location);

        if (file.getParentFile().isDirectory())
          throw new ResourceAuthorisationFailureException(location);

        throw new ResourcePathNotFoundForWriteException(e, location);
      }
    }

    @Override
    public Charset getCharacterSet() {
      return Charset.forName("UTF-8");
    }

    public ResourceLocation getLocation() {
      return location;
    }

    @Override
    public void store(Object content) {
    }
  }
}
