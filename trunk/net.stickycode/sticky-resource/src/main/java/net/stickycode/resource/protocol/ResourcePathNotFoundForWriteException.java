package net.stickycode.resource.protocol;

import java.io.FileNotFoundException;

import net.stickycode.exception.TransientException;
import net.stickycode.resource.ResourceLocation;

@SuppressWarnings("serial")
public class ResourcePathNotFoundForWriteException
    extends TransientException {

  public ResourcePathNotFoundForWriteException(FileNotFoundException e, ResourceLocation resourceLocation) {
    super(e, "Resource path {} is not writable or a parent path does not exist", resourceLocation.getPath());
  }

}
