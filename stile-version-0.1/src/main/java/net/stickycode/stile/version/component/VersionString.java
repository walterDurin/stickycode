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

public abstract class VersionString
    implements CharSequence {

  private final CharSequence source;

  private final int separator;

  private final int start;

  private final int end;

  public VersionString(CharSequence source, int separator, int start, int end) {
    if (separator == end || start == end)
      throw new RuntimeException("Invalidate version spec " + source);

    this.source = source;
    this.separator = separator;
    this.start = start;
    this.end = end;
  }

  @Override
  public int length() {
    return end - start;
  }

  @Override
  public char charAt(int index) {
    return source.charAt(start + index);
  }

  @Override
  public CharSequence subSequence(int i, int k) {
    return source.subSequence(start + i, start + k);
  }

  abstract boolean isNumber();

  abstract boolean isString();

  int getEnd() {
    return end;
  }

  boolean hasSeparator() {
    return start > separator;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public String toString() {
    return source.subSequence(start, end).toString();
  }

  public String fullString() {
    return source.subSequence(separator, end).toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof VersionString))
      return false;

    return toString().equals(obj.toString());
  }

  public String getSeparator() {
    return source.subSequence(separator, start).toString();
  }

  public NumericVersionString asNumeric() {
    throw new IllegalStateException();
  }

  public CharacterVersionString asCharacter() {
    throw new IllegalStateException();
  }

  public boolean isQualifier() {
    return false;
  }
}
