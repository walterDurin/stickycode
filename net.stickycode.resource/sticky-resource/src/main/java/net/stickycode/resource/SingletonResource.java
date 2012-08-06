package net.stickycode.resource;

import java.io.IOException;
import java.nio.charset.Charset;

import net.stickycode.stereotype.resource.Resource;

public class SingletonResource<T>
    implements Resource<T> {

  private T loadedResource;

  @SuppressWarnings("unchecked")
  public SingletonResource(ResourceCodec<?> codec, ResourceLocation location, ResourceProtocol protocol) {
    ResourceInput input = protocol.createInput(location);
    try {
      this.loadedResource = (T) input.load(location.getResourceTarget(), codec, Charset.forName("UTF-8"));
    }
    finally {
      try {
        input.close();
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

  @Override
  public T get() {
    return loadedResource;
  }

  public void set(T value) {
    throw new UnsupportedOperationException("A singleton resource cannot be updated");
  }

}
