package net.stickycode.stereotype;

import org.junit.Test;


public class ConfiguredTest {


  public class ConfiguredBean {
    @Configured
    String configured;

    @Configured(secret=true)
    String password;

    @Configured("Set to true if the light should be on")
    Boolean lightOn;

  }

  @Test
  public void validate() {
    new ConfiguredBean();
  }
}
