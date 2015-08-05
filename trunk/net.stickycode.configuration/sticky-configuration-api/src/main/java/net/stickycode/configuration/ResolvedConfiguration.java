package net.stickycode.configuration;

public interface ResolvedConfiguration
{

  /**
   * Return the most important resolve value for the relevent configuration.
   */
  String getValue();

  /**
   * Add a value to this resolution.
   */
  void add(ConfigurationValue value);

  /**
   * @return true if this configuration values has at least one value provided by a configuration source.
   */
  boolean hasValue();

}
