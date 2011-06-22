package net.stickycode.stile.version.component;


class NumericVersionComponent
    extends AbstractVersionComponent {

  public NumericVersionComponent(NumericVersionString s) {
    super(s);
  }

  @Override
  public ComponentOrdering getOrdering() {
    return ComponentOrdering.Release;
  }

  @Override
  protected int valueHashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean valueEquals(Object obj) {
    NumericVersionComponent other = (NumericVersionComponent) obj;
    return toString().equals(other.toString());
  }

}
