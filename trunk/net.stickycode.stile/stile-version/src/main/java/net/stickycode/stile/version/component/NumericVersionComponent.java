package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;
import net.stickycode.stile.version.VersionComponent;

class NumericVersionComponent
    extends VersionComponent<Integer> {

  private Integer value;

  public NumericVersionComponent(Integer value) {
    this.value = notNull(value, "Numeric versions cannot be null");
  }

  @Override
  protected Integer getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

}
