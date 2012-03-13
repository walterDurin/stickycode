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

public class ComponentVersionParserTest {

  @Test(expected = NullPointerException.class)
  public void nullVersion() {
    new ComponentVersionParser().parse(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyVersion() {
    new ComponentVersionParser().parse("");
  }

  @Test
  public void numeric() {
    check("1", n(1));
    check("2", n(2));
  }

  @Test
  public void twoNumeric() {
    check("2.1", n(2), n(1));
    check("0.1", n(0), n(1));
    check("0-1", n(0), n(1));
  }

  private NumericVersionComponent n(int i) {
    String string = Integer.toString(i);
    return new NumericVersionComponent(new NumericVersionString(string, 0, 0, string.length()));
  }

  @Test
  public void revisionPostRelease() {
    check("1r1", r(1), n(1));
    check("1r2", r(1), n(2));
    check("1r0-1", r(1), n(0), n(1));
  }

  private NumericVersionComponent r(int i) {
    NumericVersionComponent n = n(i);
    n.qualify(ComponentOrdering.Revision, new CharacterVersionString("r", 0, 0, 1));
    return n;
  }

  private NumericVersionComponent p(int i) {
    NumericVersionComponent n = n(i);
    n.qualify(ComponentOrdering.Patch, new CharacterVersionString("p", 0, 0, 1));
    return n;
  }

  @Test
  public void patchesPostRelease() {
    check("1p1", p(1), n(1));
    check("2p2", p(2), n(2));
    check("3p10", p(3), n(10));
    check("4p10-1", p(4), n(10), n(1));
  }

  @Test
  public void singlePointStringVersion() {
    check("a", s("a"));
    check("b", s("b"));
    check("a-a", s("a"), s("a"));
  }

  private StringVersionComponent s(String string) {
    return new StringVersionComponent(new CharacterVersionString(string, 0, 0, string.length()));
  }

  @Test
  public void greekVersion() {
    check("alpha", s("alpha"));
    check("alPha", s("alPha"));
    check("ALPHA", s("ALPHA"));
    check("1-ALPHA", qualified(1, "ALPHA"));

    check("beta", s("beta"));
    check("Beta", s("Beta"));
    check("BETA", s("BETA"));
    check("1-BETA", qualified(1, "BETA"));

    check("fcs", s("fcs"));

    check("rc", s("rc"));
  }

  @Test
  public void others() {
    check("0.9.0-incubator-SNAPSHOT", n(0), n(9), n(0), qualified("incubator", "SNAPSHOT"));
    check("0.9.5-RC2", n(0), n(9), qualified(5, "RC"), n(2));
    check("0.9.5-SNAPSHOT", n(0), n(9), qualified(5, "SNAPSHOT"));
    check("1.9.5.GA", n(1), n(9), qualified(5, "GA"));
    check("1.0.0.PFD-3-jboss-1", n(1), n(0), n(0), s("PFD"), n(3), s("jboss"), n(1));
    check("1.0.1B-rc4", n(1), n(0), n(1), qualified("B", "rc"), n(4));
    check("1.0-alpha-10", n(1), qualified(0, "alpha"), n(10));
    // check("1.0-alpha-11", new NumericVersionComponent(1));
    // check("1.0-alpha-12", new NumericVersionComponent(1));
    // check("1.0-alpha-13", new NumericVersionComponent(1));
    // check("1.0-alpha-14", new NumericVersionComponent(1));
    // check("1.0-alpha-15", new NumericVersionComponent(1));
    // check("1.0-alpha-16", new NumericVersionComponent(1));
    // check("1.0-alpha-17", new NumericVersionComponent(1));
    // check("1.0-alpha-18", new NumericVersionComponent(1));
    // check("1.0-alpha-19", new NumericVersionComponent(1));
    // check("1.0-alpha-1", new NumericVersionComponent(1));
    // check("1.0-alpha-1-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.0-alpha-20", new NumericVersionComponent(1));
    // check("1.0-alpha-21", new NumericVersionComponent(1));
    // check("1.0-alpha-2-20061214.035657-1", new NumericVersionComponent(1));
    // check("1.0-alpha-22", new NumericVersionComponent(1));
    // check("1.0-alpha-2", new NumericVersionComponent(1));
    // check("1.0-alpha-30", new NumericVersionComponent(1));
    // check("1.0-alpha-34", new NumericVersionComponent(1));
    // check("1.0-alpha-3", new NumericVersionComponent(1));
    // check("1.0-alpha-4", new NumericVersionComponent(1));
    // check("1.0-alpha-4-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.0-alpha-4-sonatype", new NumericVersionComponent(1));
    // check("1.0-alpha-5", new NumericVersionComponent(1));
    // check("1.0-alpha-6", new NumericVersionComponent(1));
    // check("1.0-alpha-7", new NumericVersionComponent(1));
    // check("1.0-alpha-7-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.0-alpha-8", new NumericVersionComponent(1));
    // check("1.0-alpha-9", new NumericVersionComponent(1));
    // check("1.0-alpha-9-stable-1", new NumericVersionComponent(1));
    // check("1.0.b2", new NumericVersionComponent(1));
    // check("1.0b3", new NumericVersionComponent(1));
    // check("1.0-beta-1", new NumericVersionComponent(1));
    // check("1.0-beta-2", new NumericVersionComponent(1));
    // check("1.0-beta-3", new NumericVersionComponent(1));
    // check("1.0-beta-4", new NumericVersionComponent(1));
    // check("1.0-beta-6", new NumericVersionComponent(1));
    // check("1.0-beta-7", new NumericVersionComponent(1));
    // check("1.0-beta-9", new NumericVersionComponent(1));
    // check("1.0-FCS", new NumericVersionComponent(1));
    // check("1.0-jsr-03", new NumericVersionComponent(1));
    // check("1.0-rc1-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.0-rc-2", new NumericVersionComponent(1));
    // check("1.0-RC2", new NumericVersionComponent(1));
    // check("1.0-rc-3", new NumericVersionComponent(1));
    // check("1.0-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.1.0-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.1.1-dev", new NumericVersionComponent(1));
    // check("1.1.1-ea-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.1.4c", new NumericVersionComponent(1));
    // check("1.1-alpha-2", new NumericVersionComponent(1));
    // check("1.1-beta-4", new NumericVersionComponent(1));
    // check("1.1-beta-8", new NumericVersionComponent(1));
    // check("1.1-SNAPSHOT", new NumericVersionComponent(1));
    // check("1.2-alpha-6", new NumericVersionComponent(1));
    // check("1.2-alpha-7", new NumericVersionComponent(1));
    // check("1.2-alpha-9", new NumericVersionComponent(1));
    // check("1.2-dev1", new NumericVersionComponent(1));
    // check("1.2_Java1.3", new NumericVersionComponent(1));
    // check("1.3.1.GA", new NumericVersionComponent(1));
    // check("1.3rc1", new NumericVersionComponent(1));
    // check("1.4-alpha-1", new NumericVersionComponent(1));
    // check("1.9.18d", new NumericVersionComponent(1));
    // check("2.0.0-M6", new NumericVersionComponent(1));
    // check("2.0.0.RELEASE", new NumericVersionComponent(1));
    // check("2.0-alpha-3", new NumericVersionComponent(1));
    // check("2.0-alpha-4", new NumericVersionComponent(1));
    // check("2.0b4", new NumericVersionComponent(1));
    // check("2.0-beta-1", new NumericVersionComponent(1));
    // check("2.0-beta-2", new NumericVersionComponent(1));
    // check("2.0-beta-3", new NumericVersionComponent(1));
    // check("2.0-beta-3-SNAPSHOT", new NumericVersionComponent(1));
    // check("2.0-beta-4", new NumericVersionComponent(1));
    // check("2.0-beta-5", new NumericVersionComponent(1));
    // check("2.0-beta-6", new NumericVersionComponent(1));
    // check("2.0-beta-7", new NumericVersionComponent(1));
    // check("2.0-beta-8", new NumericVersionComponent(1));
    // check("2.0-beta-9", new NumericVersionComponent(1));
    // check("2.1.1-SNAPSHOT", new NumericVersionComponent(1));
    // check("2.1.2.GA", new NumericVersionComponent(1));
    // check("2.1-alpha-1", new NumericVersionComponent(1));
    // check("2.1-beta-1", new NumericVersionComponent(1));
    // check("2.1-nodep", new NumericVersionComponent(1));
    // check("2.1.v20091210", new NumericVersionComponent(1));
    // check("2.2-beta-1", new NumericVersionComponent(1));
    // check("2.2-beta-2", new NumericVersionComponent(1));
    // check("2.2-beta-3", new NumericVersionComponent(1));
    // check("2.2-beta-4", new NumericVersionComponent(1));
    // check("2.2-beta-5", new NumericVersionComponent(1));
    // check("2.2-SNAPSHOT", new NumericVersionComponent(1));
    // check("[2.5.6.SEC02]", new NumericVersionComponent(1));
    // check("2.5.6.SEC02", new NumericVersionComponent(1));
    // check("2.5.D1", new NumericVersionComponent(1));
    // check("2.5-SNAPSHOT", new NumericVersionComponent(1));
    // check("3.0.2-FINAL", new NumericVersionComponent(1));
    // check("3.0.3.RELEASE", new NumericVersionComponent(1));
    // check("[3.0.4.RELEASE]", new NumericVersionComponent(1));
    // check("3.0.4.RELEASE", new NumericVersionComponent(1));
    // check("3.0.5.RELEASE", new NumericVersionComponent(1));
    // check("3.0-beta-1-SNAPSHOT", new NumericVersionComponent(1));
    // check("3.0-beta-3", new NumericVersionComponent(1));
    // check("3.0.PFD20090525", new NumericVersionComponent(1));
    // check("3.0-rc3", new NumericVersionComponent(1));
    // check("3.2.4.Final", new NumericVersionComponent(1));
    // check("3.3.0-v20070604", new NumericVersionComponent(1));
    // check("3.8.0.GA", new NumericVersionComponent(1));
    // check("3.9.0.GA", new NumericVersionComponent(1));
    // check("4.0.2.GA", new NumericVersionComponent(1));
    // check("4.1.0.GA", new NumericVersionComponent(1));
    // check("4aug2000r7-dev", new NumericVersionComponent(1));
    // check("7.0.0.v20091005", new NumericVersionComponent(1));
    // check("7.0.1.v20091125", new NumericVersionComponent(1));
    // check("7.1.4.v20100610", new NumericVersionComponent(1));
    // check("8.4-701.jdbc4", new NumericVersionComponent(1));
    // check("build210", new NumericVersionComponent(1));
    // check("r09", new NumericVersionComponent(1));
    // check("snapshot-20080530", new NumericVersionComponent(1));
  }

  private NumericVersionComponent qualified(int i, String o) {
    NumericVersionComponent n = n(i);
    ComponentOrdering ordering = ComponentOrdering.fromCode(o);
    n.qualify(ordering, new CharacterVersionString(o, 0, 0, o.length()));
    return n;
  }

  private StringVersionComponent qualified(String i, String o) {
    StringVersionComponent n = s(i);
    ComponentOrdering ordering = ComponentOrdering.fromCode(o);
    n.qualify(ordering, new CharacterVersionString(o, 0, 0, o.length()));
    return n;
  }

  private void check(String versionString, AbstractVersionComponent... versionComponent) {
    ComponentVersion v = new ComponentVersionParser().parse(versionString);
    assertThat(v).containsOnly(versionComponent);
  }

}
