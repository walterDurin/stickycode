package net.stickycode.stile.artifact;

import static net.stickycode.exception.Preconditions.notNull;

import java.util.LinkedList;
import java.util.List;

import net.stickycode.stile.version.Version;
import net.stickycode.stile.version.component.ComponentVersion;
import net.stickycode.util.MultiLinked;

public class Artifact {

  private final String id;

  private final Version version;

  private final MultiLinked<Sphere, Dependency> dependencies = new MultiLinked<Sphere, Dependency>();
  private final List<Dependency> main = new LinkedList<Dependency>();

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id.hashCode();
    result = prime * result + version.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    Artifact other = (Artifact) obj;
    if (!id.equals(other.id))
      return false;

    return version.equals(other.version);
  }

  @Override
  public String toString() {
    return id + '-' + version;
  }

  public List<Dependency> getDependencies(Sphere sphere) {
    return main;
  }

  public void addDependency(Sphere sphere, Dependency dependency) {
    main.add(dependency);
  }

}
