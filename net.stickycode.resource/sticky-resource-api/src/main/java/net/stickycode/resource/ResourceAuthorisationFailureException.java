package net.stickycode.resource;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ResourceAuthorisationFailureException
    extends TransientException {

  public ResourceAuthorisationFailureException(ResourceLocation resourceLocation) {
    super("Access to resource {} is not authorised", resourceLocation.getPath());
  }

}
