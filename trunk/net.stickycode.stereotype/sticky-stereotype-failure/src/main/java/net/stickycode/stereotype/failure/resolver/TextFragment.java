package net.stickycode.stereotype.failure.resolver;

public class TextFragment
    extends Fragment {

  private String value;

  public TextFragment(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (getClass() != obj.getClass())
      return false;
    TextFragment other = (TextFragment) obj;
    return value.equals(other.value);
  }

  @Override
  public String toString() {
    return super.toString() + "[" + value + "]";
  }

  @Override
  public boolean isText() {
    return true;
  }

}
