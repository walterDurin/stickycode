package net.stickycode.configured.spring4;

import java.util.Map;

import javax.inject.Inject;

import net.stickycode.configured.strategy.StrategyFinder;
import net.stickycode.configured.strategy.StrategyNotFoundException;
import net.stickycode.stereotype.StickyComponent;

import org.springframework.context.ApplicationContext;

@StickyComponent
public class SpringStrategyFinder
    implements StrategyFinder {

  @Inject
  ApplicationContext context;

  @Override
  public Object findWithName(Class<?> contract, String name) throws StrategyNotFoundException {
    Map<String, ?> m = context.getBeansOfType(contract);
    Object o = m.get(name);
    if (o != null)
      return o;

    throw new StrategyNotFoundException(contract, name, m.keySet());
  }

}
