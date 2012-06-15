package net.stickycode.mockwire.guice3.provider;

import javax.inject.Provider;

import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class StuffProvider
    implements Provider<Stuff> {

  @Override
  public Stuff get() {
    return new Stuff();
  }

}
