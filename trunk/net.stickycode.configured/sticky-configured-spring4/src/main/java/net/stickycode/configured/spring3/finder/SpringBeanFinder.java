package net.stickycode.configured.spring3.finder;

import java.util.Map;

import javax.inject.Inject;

import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.BeanNotFoundException;
import net.stickycode.stereotype.StickyComponent;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

@StickyComponent
public class SpringBeanFinder
    implements BeanFinder {

  @Inject
  private ApplicationContext context;

  @Override
  public <T> T find(Class<T> type) throws BeanNotFoundException {
    try {
      return context.getBean(type);
    }
    catch (NoSuchBeanDefinitionException e) {
      try {
        Map<String, T> beans = context.getBeansOfType(type);
        if (beans.values().isEmpty())
          throw new BeanNotFoundException(e, type);

        throw new BeanNotFoundException(type, beans.values());
      }
      catch (NoSuchBeanDefinitionException e2) {
        throw new BeanNotFoundException(e2, type);
      }
    }
  }

}
