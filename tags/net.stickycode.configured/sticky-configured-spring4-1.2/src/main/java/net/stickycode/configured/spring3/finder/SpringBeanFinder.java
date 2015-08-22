package net.stickycode.configured.spring3.finder;

import javax.inject.Inject;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.BeanNotFoundException;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class SpringBeanFinder
    implements BeanFinder {

  @Inject
  private ComponentContainer context;

  @Override
  public <T> T find(Class<T> type) throws BeanNotFoundException {
    return context.find(type);
  }

}
