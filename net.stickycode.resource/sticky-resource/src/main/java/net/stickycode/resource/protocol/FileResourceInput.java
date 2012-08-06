package net.stickycode.resource.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;

public class FileResourceInput
    implements ResourceInput {

  private InputStream stream;

  public FileResourceInput(ResourceLocation location) {
    this.stream = buildStream(location);
  }

  public InputStream buildStream(ResourceLocation location)
      throws ResourceNotFoundException {
    String path = location.getPath();
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

  @Override
  public void close() {
    try {
      stream.close();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T load(CoercionTarget resourceTarget, ResourceCodec<T> codec, Charset characterSet) {
    return codec.load(resourceTarget, stream, characterSet);
  }

}
