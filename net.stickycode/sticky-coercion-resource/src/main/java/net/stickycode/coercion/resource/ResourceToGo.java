package net.stickycode.coercion.resource;

import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.ConfiguredComponent;

@ConfiguredComponent
public class ResourceToGo<T> {

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

}
