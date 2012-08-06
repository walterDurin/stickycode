package net.stickycode.resource.protocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceAuthorisationFailureException;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;

public class FileResourceOutput
    implements ResourceOutput {

  private OutputStream stream;

  public FileResourceOutput(ResourceLocation location) {
    this.stream = buildStream(location);
  }

  private FileOutputStream buildStream(ResourceLocation location) {
    File file = new File(location.getPath());
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
  public void close()
      throws IOException {
  }

  @Override
  public <T> void store(T value, CoercionTarget type, ResourceCodec<T> codec) {
    codec.store(type, value, stream, Charset.forName("UTF-8"));
  }

}
