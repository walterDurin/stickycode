package net.stickycode.configured.content;

import net.stickycode.configuration.ConfigurationTarget;
import net.stickycode.stereotype.content.Content;

public interface LocalisedAttribute
    extends ConfigurationTarget {

  void setValue(String value);

  void setValue(Content content);

  String getValue();

}
