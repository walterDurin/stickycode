package net.stickycode.stile.version.component;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class ExternalComponentVersionParserTest {

  @Test
  public void others() {
    check("0.9.0");
    check("0.9");
    check("9.0-incubator-SNAPSHOT");
    check("9");
    check("999");
    check("0.9.0-incubator-SNAPSHOT");
    check("0.9.5-RC2");
    check("0.9.9-SNAPSHOT");
    check("1.0.0.GA");
    check("1.0.0-PFD-3-jboss-1");
    check("1.0.1B-rc4");
    check("1.0-alpha-10");
    check("1.0-alpha-11");
    check("1.0-alpha-12");
    check("1.0-alpha-13");
    check("1.0-alpha-14");
    check("1.0-alpha-15");
    check("1.0-alpha-16");
    check("1.0-alpha-17");
    check("1.0-alpha-18");
    check("1.0-alpha-19");
    check("1.0-alpha-1");
    check("1.0-alpha-1-SNAPSHOT");
    check("1.0-alpha-20");
    check("1.0-alpha-21");
    check("1.0-alpha-2-20061214.035657-1");
    check("1.0-alpha-22");
    check("1.0-alpha-2");
    check("1.0-alpha-30");
    check("1.0-alpha-34");
    check("1.0-alpha-3");
    check("1.0-alpha-4");
    check("1.0-alpha-4-SNAPSHOT");
    check("1.0-alpha-4-sonatype");
    check("1.0-alpha-5");
    check("1.0-alpha-6");
    check("1.0-alpha-7");
    check("1.0-alpha-7-SNAPSHOT");
    check("1.0-alpha-8");
    check("1.0-alpha-9");
    check("1.0-alpha-9-stable-1");
    check("1.0.b2");
    check("1.0b3");
    check("1.0-beta-1");
    check("1.0-beta-2");
    check("1.0-beta-3");
    check("1.0-beta-4");
    check("1.0-beta-6");
    check("1.0-beta-7");
    check("1.0-beta-9");
    check("1.0-FCS");
    check("1.0-jsr-03");
    check("1.0-rc1-SNAPSHOT");
    check("1.0-rc-2");
    check("1.0-RC2");
    check("1.0-rc-3");
    check("1.0-SNAPSHOT");
    check("1.1.0-SNAPSHOT");
    check("1.1.1-dev");
    check("1.1.1-ea-SNAPSHOT");
    check("1.1.4c");
    check("1.1-alpha-2");
    check("1.1-beta-4");
    check("1.1-beta-8");
    check("1.1-SNAPSHOT");
    check("1.2-alpha-6");
    check("1.2-alpha-7");
    check("1.2-alpha-9");
    check("1.2-dev1");
    check("1.2_Java1.3");
    check("1.3.1.GA");
    check("1.3rc1");
    check("1.4-alpha-1");
    check("1.9.18d");
    check("2.0.0-M6");
    check("2.0.0.RELEASE");
    check("2.0-alpha-3");
    check("2.0-alpha-4");
    check("2.0b4");
    check("2.0-beta-1");
    check("2.0-beta-2");
    check("2.0-beta-3");
    check("2.0-beta-3-SNAPSHOT");
    check("2.0-beta-4");
    check("2.0-beta-5");
    check("2.0-beta-6");
    check("2.0-beta-7");
    check("2.0-beta-8");
    check("2.0-beta-9");
    check("2.1.1-SNAPSHOT");
    check("2.1.2.GA");
    check("2.1-alpha-1");
    check("2.1-beta-1");
    check("2.1-nodep");
    check("2.1.v20091210");
    check("2.2-beta-1");
    check("2.2-beta-2");
    check("2.2-beta-3");
    check("2.2-beta-4");
    check("2.2-beta-5");
    check("2.2-SNAPSHOT");
//    check("[2.5.6.SEC02]");
//    check("2.5.6.SEC02");
//    check("2.5.D1");
//    check("2.5-SNAPSHOT");
//    check("3.0.2-FINAL");
//    check("3.0.3.RELEASE");
//    check("[3.0.4.RELEASE]");
//    check("3.0.4.RELEASE");
//    check("3.0.5.RELEASE");
//    check("3.0-beta-1-SNAPSHOT");
//    check("3.0-beta-3");
//    check("3.0.PFD20090525");
//    check("3.0-rc3");
//    check("3.2.4.Final");
//    check("3.3.0-v20070604");
//    check("3.8.0.GA");
//    check("3.9.0.GA");
//    check("4.0.2.GA");
//    check("4.1.0.GA");
//    check("4aug2000r7-dev");
//    check("7.0.0.v20091005");
//    check("7.0.1.v20091125");
//    check("7.1.4.v20100610");
//    check("8.4-701.jdbc4");
//    check("build210");
//    check("r09");
//    check("snapshot-20080530");
  }

  @Test(timeout=2000)
  @Ignore
  public void load() {
    for (int i = 0; i < 10; i++) {
      others();
    }
  }

  ComponentVersionParser parser = new ComponentVersionParser();

  private void check(String versionString) {
    ComponentVersion v = parser.parse(versionString);
    assertThat(v).isNotNull();
  }
}
