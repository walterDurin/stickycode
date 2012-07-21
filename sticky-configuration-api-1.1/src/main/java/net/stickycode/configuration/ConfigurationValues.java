package net.stickycode.configuration;


public interface ConfigurationValues
{

  String getValue();

  void add(Value value);

  /**
   * @return true if this configuration values has at least one value provided by a configuration source.
   */
  boolean hasValue();


}
