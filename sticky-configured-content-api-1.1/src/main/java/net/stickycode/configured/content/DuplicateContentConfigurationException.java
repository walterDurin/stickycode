package net.stickycode.configured.content;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class DuplicateContentConfigurationException
    extends PermanentException {

  public DuplicateContentConfigurationException(LocalisedElement contentConfiguration) {
    super("Found a exiting content definition for key {} when registering {}",
        contentConfiguration.getKey(), contentConfiguration);

  }

}
