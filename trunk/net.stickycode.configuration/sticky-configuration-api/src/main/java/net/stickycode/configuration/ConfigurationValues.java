package net.stickycode.configuration;



public interface ConfigurationValues {

  String getValue();

  void add(Value value);

  boolean hasValue();

}
