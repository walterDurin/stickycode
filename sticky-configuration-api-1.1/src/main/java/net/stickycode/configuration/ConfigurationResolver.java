package net.stickycode.configuration;


public interface ConfigurationResolver {

  ConfigurationResolutions resolve(Iterable<ConfigurationKey> key);
  
}
