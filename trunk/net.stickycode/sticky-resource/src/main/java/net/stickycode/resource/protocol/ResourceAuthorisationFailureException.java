package net.stickycode.resource.protocol;

import net.stickycode.exception.TransientException;
import net.stickycode.resource.ResourceLocation;

@SuppressWarnings("serial")
public class ResourceAuthorisationFailureException
    extends TransientException {

  public ResourceAuthorisationFailureException(ResourceLocation resourceLocation) {
    super("Access to resource {} is not authorised", resourceLocation.getPath());
  }

}
