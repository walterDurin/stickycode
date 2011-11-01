package net.stickycode.configured.content;

import net.stickycode.configured.ConfigurationKeyElement;
import net.stickycode.stereotype.ui.Content;

public interface LocalisedAttribute
    extends ConfigurationKeyElement {

  void setValue(String value);

  void setValue(Content content);

  String getValue();

}
