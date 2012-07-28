package net.stickycode.configured.content;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.stereotype.content.Content;

public interface LocalisedAttribute
    extends ConfigurationKey {

  void setValue(String value);

  void setValue(Content content);

  String getValue();

}
