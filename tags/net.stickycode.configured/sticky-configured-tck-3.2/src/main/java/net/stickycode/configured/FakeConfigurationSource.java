package net.stickycode.configured;

import net.stickycode.stereotype.StickyPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyPlugin
public class FakeConfigurationSource
    implements ConfigurationSource {

  private Logger log = LoggerFactory.getLogger(getClass());
  
  @Override
  public boolean hasValue(String key) {
    log.debug("has value {}", key);
    if (key.endsWith(".bob"))
      return true;

    if (key.endsWith(".numbers"))
      return true;

    return false;
  }

  @Override
  public String getValue(String key) throws ConfigurationNotFoundException {
    if (key.endsWith(".bob"))
      return "yay";

    if (key.endsWith(".numbers"))
      return "1,5,3,7";

    throw new ConfigurationNotFoundException(key);
  }

}
