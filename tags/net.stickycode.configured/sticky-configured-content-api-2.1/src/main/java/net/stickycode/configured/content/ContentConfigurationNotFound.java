package net.stickycode.configured.content;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ContentConfigurationNotFound
    extends PermanentException {

  public ContentConfigurationNotFound(String key) {
    super("Content was not found for key {}", key);
  }

}
