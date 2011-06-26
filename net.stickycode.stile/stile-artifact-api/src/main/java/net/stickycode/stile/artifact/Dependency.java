package net.stickycode.stile.artifact;

import static net.stickycode.exception.Preconditions.notNull;
import net.stickycode.stile.version.VersionRange;
import net.stickycode.util.Linked;

public class Dependency
    implements Linked<Dependency> {

  private final String id;

  private final VersionRange version;

  public Dependency next;

  public Dependency(String id, VersionRange version) {
    super();
    this.id = id;
    this.version = version;
  }

  @Override
  public Dependency getNext() {
    return next;
  }

  @Override
  public void setNext(Dependency node) {
    this.next = notNull(node, "Cannot set next to null");
  }

  public String getId() {
    return id;
  }

  public VersionRange getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return id + "~" + version;
  }
}
