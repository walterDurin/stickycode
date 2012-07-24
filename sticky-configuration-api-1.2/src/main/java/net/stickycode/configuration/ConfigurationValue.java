package net.stickycode.configuration;

public interface ConfigurationValue {

  String get();

  boolean hasPrecedence(ConfigurationValue v);

}
