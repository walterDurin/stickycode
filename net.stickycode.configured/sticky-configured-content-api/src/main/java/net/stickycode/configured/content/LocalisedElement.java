package net.stickycode.configured.content;

import net.stickycode.configuration.ConfigurationKey;

public interface LocalisedElement
    extends Iterable<LocalisedAttribute>, ConfigurationKey {

  /**
   * The key uniquely identifying the content
   */
  String getKey();

  /** Add a localised attribute to this element */
  void addContent(LocalisedAttribute localisedAttribute);

}
