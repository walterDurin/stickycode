package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;

import net.stickycode.stile.version.component.VersionDefinition;

class DefinedStringVersionComponent
    extends AbstractVersionComponent {

  private VersionDefinition value;

  public DefinedStringVersionComponent(VersionDefinition v) {
    this.value = notNull(v, "Version definition cannot be null");
  }

  @Override
  public String toString() {
    return value.name();
  }

  @Override
  public ComponentOrdering getOrdering() {
    return ComponentOrdering.Prerelease;
  }
  
  @Override
  protected int valueHashCode() {
    return value.hashCode();
  }
  
  @Override
  public boolean valueEquals(Object obj) {
    DefinedStringVersionComponent other = (DefinedStringVersionComponent) obj;
    return value.equals(other.value);
  }

}

