package net.stickycode.configured.content;

import java.util.Locale;

public interface LocaleProvider {

  /**
   * Get the current locale, can be contextural e.g. Thread Scoped, Application Scoped
   */
  Locale get();

  /**
   * If this provider is a singleton then it can be used to inject Strings marks as ConfiguredContent.
   * 
   */
  boolean isSingleton();

}
