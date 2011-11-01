package net.stickycode.configured.content;

import java.util.Locale;

import javax.inject.Provider;

/**
 * A source of localised content.
 */
public interface LocalisedContentSource {

  /**
   * Return true if this content source can provide content for the given key.
   */
  boolean hasContent(String key);

  /**
   * Get {@link Locale}ised content for the given key
   */
  String getContent(String key, Locale locale)
      throws ContentConfigurationNotFound;

  /**
   * Return a means of getting the {@link Locale}ised content in the context of a Locale. For example where the
   * locale is related to a users request then it might be request scoped.
   * 
   * While a deployed desktop application would have have content in application scope.
   */
  Provider<String> getContentProvider(String key)
      throws ContentConfigurationNotFound;

}
