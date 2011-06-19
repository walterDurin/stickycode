package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notBlank;
import net.stickycode.exception.Preconditions;

class StringVersionComponent
    extends AbstractVersionComponent {

  private final String value;

  public StringVersionComponent(String value) {
    this.value = notBlank(value, "String versions cannot be blank");
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public ComponentOrdering getOrdering() {
    return ComponentOrdering.Named;
  }

  @Override
  protected int valueHashCode() {
    return value.hashCode();
  }

  @Override
  public boolean valueEquals(Object obj) {
    StringVersionComponent other = (StringVersionComponent) obj;
    return value.equals(other.value);
  }

}
