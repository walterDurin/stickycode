package net.stickycode.resource;

import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.resource.Resource;

//@ConfiguredComponent
@StickyComponent
public class StickyResource<T>
    implements Resource<T> {

  @Configured
  private ResourceLocation uri;

  @Configured
  private ResourceCodec<T> codec;

  // @Configured
  // ResourceLoader loader;

  public T get() {
    if (codec == null)
      throw new RuntimeException();

    if (uri == null)
      throw new RuntimeException();

    return codec.load(uri.getInputStream(), uri.getResourceTarget());
  }
  
  public void set(T value) {
    codec.store(uri.getResourceTarget(), value, uri.getOutputStream());
  }

}
