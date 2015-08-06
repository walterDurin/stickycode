package net.stickycode.configuration;

/**
 * A single value of configuration that was resolved for a configuration key, it has defined.
 */
public interface ConfigurationValue {

  /**
   * Get the configuration value.
   */
  String get();

  /**
   * Return true if this configuration is more important the given value.
   */
  boolean hasPrecedence(ConfigurationValue v);

}
