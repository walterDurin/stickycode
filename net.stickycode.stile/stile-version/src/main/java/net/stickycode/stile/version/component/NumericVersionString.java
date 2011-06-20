package net.stickycode.stile.version.component;

public class NumericVersionString
    extends VersionString {

  public NumericVersionString(CharSequence source, int separator, int start, int end) {
    super(source, separator, start, end);
  }

  @Override
  boolean isNumber() {
    return true;
  }

  @Override
  boolean isString() {
    return false;
  }

}
