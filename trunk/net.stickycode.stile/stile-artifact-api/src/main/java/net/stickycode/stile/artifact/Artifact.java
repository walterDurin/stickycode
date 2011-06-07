package net.stickycode.stile.artifact;

import static net.stickycode.exception.Preconditions.notNull;
import net.stickycode.stile.version.Version;

public class Artifact {

  private final String id;

  private final Version version;

  public Artifact(String id, Version version) {
    this.id = notNull(id, "Id cannot be null");
    this.version = notNull(version, "Version cannot be null");
  }

  public Version getVersion() {
    return version;
  }

  public String getId() {
    return id;
  }

}
