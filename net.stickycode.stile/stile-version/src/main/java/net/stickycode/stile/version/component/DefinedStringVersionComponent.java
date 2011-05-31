package net.stickycode.stile.version.component;

import static net.stickycode.stile.version.Prerequisites.notNull;

import net.stickycode.stile.version.VersionComponent;
import net.stickycode.stile.version.component.VersionDefinition;

class DefinedStringVersionComponent
    extends VersionComponent<VersionDefinition> {

  private VersionDefinition value;

  public DefinedStringVersionComponent(VersionDefinition v) {
    this.value = notNull(v, "Version definition cannot be null");
  }

  @Override
  protected VersionDefinition getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.name();
  }

}

