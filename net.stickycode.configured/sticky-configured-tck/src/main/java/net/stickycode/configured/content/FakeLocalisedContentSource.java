package net.stickycode.configured.content;

import java.util.Locale;

import javax.inject.Provider;

import net.stickycode.configured.content.ContentConfigurationNotFound;
import net.stickycode.configured.content.LocalisedContentSource;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class FakeLocalisedContentSource
    implements LocalisedContentSource {

  @Override
  public boolean hasContent(String key) {
    return false;
  }

  @Override
  public String getContent(String key, Locale locale) throws ContentConfigurationNotFound {
    return null;
  }

  @Override
  public Provider<String> getContentProvider(String key) throws ContentConfigurationNotFound {
    return null;
  }

}
