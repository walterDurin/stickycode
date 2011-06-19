package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;

class NumericVersionComponent
    extends AbstractVersionComponent {

  private Integer value;

  public NumericVersionComponent(Integer value) {
    this.value = notNull(value, "Numeric versions cannot be null");
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public ComponentOrdering getOrdering() {
    return ComponentOrdering.Numeric;
  }

  @Override
  protected int valueHashCode() {
    return value.hashCode();
  }
  
  @Override
  public boolean valueEquals(Object obj) {
    NumericVersionComponent other = (NumericVersionComponent) obj;
    return value.equals(other.value);
  }
  
  @Override
  public int compareTo(AbstractVersionComponent o) {
    return value.compareTo(((NumericVersionComponent)o).value);
  }

}
