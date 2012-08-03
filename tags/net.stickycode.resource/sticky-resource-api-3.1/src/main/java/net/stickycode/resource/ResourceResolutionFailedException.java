package net.stickycode.resource;

import java.net.UnknownHostException;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ResourceResolutionFailedException
    extends TransientException {

  public ResourceResolutionFailedException(UnknownHostException e, ResourceLocation resourceLocation) {
    super(e, "Failed to lookup host for resource {}", resourceLocation);
  }

}
