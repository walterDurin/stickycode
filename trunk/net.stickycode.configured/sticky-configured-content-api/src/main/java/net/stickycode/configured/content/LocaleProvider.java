package net.stickycode.configured.content;

import java.util.Locale;

import net.stickycode.stereotype.ui.ConfiguredContent;

public interface LocaleProvider {

  /**
   * Get the current locale, can be contextural e.g. Thread Scoped, Application Scoped
   */
  Locale get();

  /**
   * If this provider is a singleton then we can do this
   * 
   * public class WithContent {
   * 
   * {@link ConfiguredContent} of {@link String}s can be injected, otherwise only
   * 
   */
  boolean isSingleton();

}
