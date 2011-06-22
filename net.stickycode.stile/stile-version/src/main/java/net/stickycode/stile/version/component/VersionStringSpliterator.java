package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notBlank;

import java.util.Iterator;

public class VersionStringSpliterator
    implements Iterator<VersionString> {

  private final CharSequence source;

  private int position = 0;

  public VersionStringSpliterator(String source) {
    this.source = notBlank(source, "Source cannot be blank");
  }

  @Override
  public boolean hasNext() {
    return position < source.length();
  }

  @Override
  public VersionString next() {
    VersionString nextSeparator = nextSeparator();

    position = nextSeparator.getEnd();

    return nextSeparator;
  }

  private VersionString nextSeparator() {
    int start = position;
    while (isSeparator(source.charAt(start))) {
      ++start;
      if (start == source.length())
        throw new RuntimeException("ug");
    }

    if (Character.isDigit(source.charAt(start)))
      return nextNumeric(start);

    return nextCharacter(start);
  }

  private VersionString nextNumeric(int start) {
    int end = start;
    while (Character.isDigit(source.charAt(end))) {
      ++end;
      if (end >= source.length())
        return new NumericVersionString(source, position, start, end);
    }

    return new NumericVersionString(source, position, start, end);
  }

  private VersionString nextCharacter(int start) {
    int end = start;
    while (Character.isLetter(source.charAt(end))) {
      ++end;
      if (end >= source.length())
        return new CharacterVersionString(source, position, start, end);
    }

    if (end == start)
      throw new InvalidVersionStringException(source, start);

    return new CharacterVersionString(source, position, start, end);
  }

  private boolean isSeparator(char charAt) {
    switch (charAt) {
    case '.':
    case '_':
    case '-':
      return true;
    default:
      return false;
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Iterating a version string is immutable");
  }

}
