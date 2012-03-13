/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
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
    case ' ':
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
