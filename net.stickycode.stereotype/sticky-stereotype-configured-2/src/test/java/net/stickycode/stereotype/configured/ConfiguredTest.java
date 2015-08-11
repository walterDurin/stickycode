package net.stickycode.stereotype.configured;

import net.stickycode.stereotype.configured.Configured;

import org.junit.Test;

/**
 * This test is just here as an example of what it could look like to configure a bean.
 */
public class ConfiguredTest {

  public class ConfiguredBean {

    @Configured
    String configured;

    @Configured
    ConfiguredSecret password;

    @Configured("Set to true if the light should be on")
    Boolean lightOn;

  }

  @Test
  public void validate() {
    new ConfiguredBean();
  }
}
