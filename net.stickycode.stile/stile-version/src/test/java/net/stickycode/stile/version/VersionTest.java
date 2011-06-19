package net.stickycode.stile.version;

import net.stickycode.stile.version.component.ComponentVersionParser;

import org.junit.Test;


public class VersionTest {

  @Test
  public void symetric() {
    symetric("1");
    symetric("1.1");
    symetric("1.1-SNAPSHOT");
    symetric("1.1-rc1");
    symetric("whatever.1.1-rc1");
    symetric("whatever-1.1-rc1");
  }
  
  @Test
  public void ordering() {
    sameSame("1", "1");
    ordered("1", "2");
    ordered("1", "12");
    ordered("1", "1-p2");
    ordered("1-p1", "1-p2");
    ordered("1-r0", "1-r1");
    ordered("1-rc1", "1");
    ordered("1-rc1", "1-rc4");
    ordered("1-alpha", "1");
    ordered("1-beta", "1");
    ordered("1-alpha", "1-beta");
    ordered("1-alpha", "1-gamma");
    ordered("1-beta", "1-gamma");
  }
  
  @Test
  public void compareNumeric() {
    sameSame("1", "1");
    ordered("1", "2");
    ordered("1", "12");
  }

  private void sameSame(String v1, String v2) {
    assertThat(parser.parse(v1)).isEqualByComparingTo(parser.parse(v2));
  }

  private void ordered(String v1, String v2) {
    ordered(parser.parse(v1), parser.parse(v2));
  }

  private void ordered(Version v1, Version v2) {
    assertThat(v1).isLessThan(v2);
  }

  private VersionAssert assertThat(Version v1) {
    return new VersionAssert(VersionAssert.class, v1);
  }

  private ComponentVersionParser parser = new ComponentVersionParser();

  private void symetric(String versionString) {
    symetric(parser.parse(versionString), parser.parse(versionString));
  }

  private void symetric(Version one, Version otherOne) {
    assertThat(one).isEqualTo(otherOne);
    assertThat(otherOne).isEqualTo(one);
  }
  
}
