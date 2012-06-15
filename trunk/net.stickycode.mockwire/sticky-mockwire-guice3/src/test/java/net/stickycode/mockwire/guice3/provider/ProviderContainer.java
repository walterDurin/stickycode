package net.stickycode.mockwire.guice3.provider;

import javax.inject.Inject;
import javax.inject.Provider;

import net.stickycode.stereotype.StickyComponent;


@StickyComponent
public class ProviderContainer {

  @Inject
  Provider<Stuff> stuff;
  
  Stuff stuff() {
    return stuff.get();
  }
  
}
