package net.stickycode.configured;


public interface ConfigurationKeyElement {
  
  /**
   * The type of the configuration key.
   */
  Class<?> getType();
  
  /**
   * The name of the configuration key.
   */
  String getName();

}
