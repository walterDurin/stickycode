package net.stickycode.stile.cli;

import net.stickycode.exception.PermanentException;
import net.stickycode.resource.Resource;


@SuppressWarnings("serial")
public class ArtifactResourceCannotBeFoundException
    extends PermanentException {

  public ArtifactResourceCannotBeFoundException(Resource resource) {
    super("Artifact definition cannot be read from {}", resource);
  }

}
