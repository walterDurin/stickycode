package net.stickycode.configuration;


public interface ResolvedConfiguration
{

  String getValue();

  void add(ConfigurationValue value);

  /**
   * @return true if this configuration values has at least one value provided by a configuration source.
   */
  boolean hasValue();


}
