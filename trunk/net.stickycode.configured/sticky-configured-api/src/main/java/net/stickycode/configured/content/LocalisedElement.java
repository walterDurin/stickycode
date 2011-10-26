package net.stickycode.configured.content;

import net.stickycode.configured.ConfigurationKeyElement;

public interface LocalisedElement
    extends Iterable<LocalisedAttribute>, ConfigurationKeyElement {

  /**
   * The key uniquely identifying the content
   */
  String getKey();

  /** Add a localised attribute to this element */
  void addContent(LocalisedAttribute localisedAttribute);

}
