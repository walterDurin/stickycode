package net.stickycode.resource;

import net.stickycode.resource.Resource;

public class SingletonResource<T>
    implements Resource<T> {

  private T loadedResource;

  public SingletonResource(ResourceCodec<?> codec, ResourceProtocol protocol, ResourceSpecification specification) {
    this.loadedResource = (T) codec.load(protocol.getInputStream(specification));
  }

  @Override
  public T get() {
    return loadedResource;
  }

}
