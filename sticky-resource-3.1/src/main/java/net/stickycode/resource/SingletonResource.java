package net.stickycode.resource;

import net.stickycode.stereotype.resource.Resource;

public class SingletonResource<T>
    implements Resource<T> {

  private T loadedResource;

  @SuppressWarnings("unchecked")
  public SingletonResource(ResourceCodec<?> codec, ResourceLocation location, ResourceProtocol protocol) {
    ResourceConnection connection = protocol.createConnection(location);
    this.loadedResource = (T) codec.load(connection, location.getResourceTarget());
  }

  @Override
  public T get() {
    return loadedResource;
  }

  public void set(T value) {
    throw new UnsupportedOperationException("A singleton resource cannot be updated");
  }

}
