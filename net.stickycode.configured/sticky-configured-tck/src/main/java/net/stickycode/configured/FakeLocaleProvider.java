package net.stickycode.configured;

import java.util.Locale;

import net.stickycode.configured.content.LocaleProvider;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class FakeLocaleProvider
    implements LocaleProvider {

  @Override
  public Locale get() {
    return new Locale("en", "NZ");
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
