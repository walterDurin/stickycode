package net.stickycode.configured.spring3;

import net.stickycode.bootstrap.spring3.StickySpringBootstrap;
import net.stickycode.configured.strategy.AbstractConfiguredStrategyTest;
import net.stickycode.configured.strategy.ConfiguredStrategyCoercion;

import org.springframework.context.support.GenericApplicationContext;

public class ConfiguredStrategyTest
    extends AbstractConfiguredStrategyTest
{

  @Override
  protected void configure(WithStrategy instance) {
    GenericApplicationContext c = createContext();

    c.getAutowireCapableBeanFactory().autowireBean(this);
    c.getAutowireCapableBeanFactory().autowireBean(instance);

    system.start();
  }

  private GenericApplicationContext createContext() {
    GenericApplicationContext c = new GenericApplicationContext();

    new StickySpringBootstrap(c).scan("net.stickycode");

    c.getBeanFactory().registerSingleton("yesStrategy", new YesStrategy());
    c.getBeanFactory().registerSingleton("noStrategy", new NoStrategy());

    c.refresh();
    return c;
  }

  @Override
  protected void configure(ConfiguredStrategyCoercion instance) {
    GenericApplicationContext context = createContext();
    context.getAutowireCapableBeanFactory().autowireBean(instance);
  }

}
