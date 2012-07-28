package net.stickycode.configured;

import net.stickycode.configuration.ConfigurationTarget;


public interface ConfigurationMetadataProcessor {
  
  void process(Object value);
  void process(ConfigurationTarget parent, Object value);

}
