package net.stickycode.resource;

import net.stickycode.resource.stereotype.Resource;


public class SingletonResource<T>
    implements Resource<T> {

  private T loadedResource;

  @SuppressWarnings("unchecked")
  public SingletonResource(ResourceCodec<?> codec, ResourceLocation location) {
    this.loadedResource = (T) codec.load(location.getInputStream(), location.getResourceTarget());
  }

  @Override
  public T get() {
    return loadedResource;
  }
  
  public void set(T value) {
    throw new UnsupportedOperationException("A singleton resource cannot be updated");
  }

}
