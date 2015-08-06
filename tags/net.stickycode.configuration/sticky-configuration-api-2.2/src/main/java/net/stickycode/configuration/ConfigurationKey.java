package net.stickycode.configuration;

import java.util.List;

/**
 * Contract for providing a list of keys to lookup to resolve configuration, the first found is used.
 */
public interface ConfigurationKey {

  List<String> join(String delimeter);

}
