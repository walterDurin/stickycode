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

    @Schedule
    public void runMe() {

    }

    @Schedule("Print the configured value to Standard.out")
    public void runMe2() {
      System.out.println(configured);
    }

  }

  @Test
  public void validate() {
    new ConfiguredBean();
  }
}
