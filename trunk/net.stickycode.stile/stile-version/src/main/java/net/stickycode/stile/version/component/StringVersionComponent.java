package net.stickycode.stile.version.component;


class StringVersionComponent
    extends AbstractVersionComponent {

  public StringVersionComponent(CharacterVersionString versionString) {
    super(versionString);
  }

  @Override
  protected int valueHashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean valueEquals(Object obj) {
    StringVersionComponent other = (StringVersionComponent) obj;
    return toString().equals(other.toString());
  }

}
