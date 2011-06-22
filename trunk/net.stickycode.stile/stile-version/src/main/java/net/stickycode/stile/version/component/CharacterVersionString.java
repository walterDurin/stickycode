package net.stickycode.stile.version.component;

public class CharacterVersionString
    extends VersionString {

  public CharacterVersionString(CharSequence source, int separator, int start, int end) {
    super(source, separator, start, end);
  }

  @Override
  public boolean isNumber() {
    return false;
  }

  @Override
  public boolean isString() {
    return true;
  }

  @Override
  public CharacterVersionString asCharacter() {
    return this;
  }

}
