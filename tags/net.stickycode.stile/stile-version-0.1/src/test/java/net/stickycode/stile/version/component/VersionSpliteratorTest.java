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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class VersionSpliteratorTest {

  @Test(expected = NullPointerException.class)
  public void nullVersion() {
    new VersionStringSpliterator(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyVersion() {
    new VersionStringSpliterator("");
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidBracket() {
    new VersionStringSpliterator("[2,3)").next();
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidQuotes() {
    new VersionStringSpliterator("'2-3'").next();
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidQuotes2() {
    for (VersionString s : new VersionStringSpliterable("2-3'"))
      assertThat(s).isNotNull();
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidComma() {
    for (VersionString s : new VersionStringSpliterable("2,3"))
      assertThat(s).isNotNull();
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidParenthesis() {
    for (VersionString s : new VersionStringSpliterable("(2,3)"))
      assertThat(s).isNotNull();
  }

  @Test(expected = InvalidVersionStringException.class)
  public void invalidParenthesis2() {
    for (VersionString s : new VersionStringSpliterable("2,3)"))
      assertThat(s).isNotNull();
  }

  @Test
  public void numeric() {
    check("1", n("1"));
    check("2", n("2"));
    check("2.1", n("2"), n("1"));
    check("2-1", n("2"), n("1"));
    check("2_1", n("2"), n("1"));
    check("2_1", n("2"), n("1"));
    check("3_1.4", n("3"), n("1"), n("4"));
    check("3 4 5", n("3"), n("4"), n("5"));
  }

  @Test
  public void revisions() {
    check("r1", s("r"), n("1"));
    check("r2", s("r"), n("2"));
    check("r1-2", s("r"), n("1"), n("2"));
    check("1.2r1", n("1"), n("2"), s("r"), n("1"));
  }

  @Test
  public void patches() {
    check("p1", s("p"), n("1"));
    check("p2", s("p"), n("2"));
    check("p10", s("p"), n("10"));
    check("p10-20", s("p"), n("10"), n("20"));
    check("10p20", n("10"), s("p"), n("20"));
  }

  @Test
  public void singlePointStringVersion() {
    check("a", s("a"));
    check("b", s("b"));
    check("a-a-alpha1", s("a"), s("a"), s("alpha"), n("1"));
  }

  @Test
  public void inTheWild() {
    check("0.9.0-incubator-SNAPSHOT", n("0"), n("9"), n("0"), s("incubator"), s("SNAPSHOT"));
    check("0.9.5-RC2", n("0"), n("9"), n("5"), s("RC"), s("2"));
    check("0.9.9-SNAPSHOT", n("0"), n("9"), n("9"), s("SNAPSHOT"));
    check("1.0.0.GA", n("1"), n("0"), n("0"), s("GA"));
    check("1.0.0-PFD-3-jboss-1", n("1"), n("0"), n("0"), s("PFD"), n("3"), s("jboss"), n("1"));
    check("1.0.1B-rc4", n("1"), n("0"), n("1"), s("B"), s("rc"), n("4"));
  }

  @Test(timeout = 2000)
  public void tooSlow() {
    for (int i = 0; i < 1000; i++) {
      numeric();
      inTheWild();
      singlePointStringVersion();
      patches();
      revisions();
    }
  }

  private void check(String versionString, VersionString... components) {
    VersionStringSpliterator v = new VersionStringSpliterator(versionString);
    assertThat(v).containsOnly(components);
    StringBuilder b = new StringBuilder();
    for (VersionString v2 : new VersionStringSpliterable(versionString)) {
      b.append(v2.getSeparator()).append(v2.toString());
    }
    assertThat(versionString).isEqualTo(b.toString());
  }

  private NumericVersionString n(String source) {
    return new NumericVersionString(source, 0, 0, source.length());
  }

  private CharacterVersionString s(String source) {
    return new CharacterVersionString(source, 0, 0, source.length());
  }

}
