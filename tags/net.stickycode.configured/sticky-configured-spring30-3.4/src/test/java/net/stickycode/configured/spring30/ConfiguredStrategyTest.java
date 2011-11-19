package net.stickycode.configured.spring30;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import net.stickycode.configured.strategy.AbstractConfiguredStrategyTest;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

public class ConfiguredStrategyTest
    extends AbstractConfiguredStrategyTest
{

  @Override
  protected void configure(WithStrategy instance) {
    GenericApplicationContext c = new GenericApplicationContext();

    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(c, false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.scan("net.stickycode");

    c.getBeanFactory().registerSingleton("yesStrategy", new YesStrategy());
    c.getBeanFactory().registerSingleton("noStrategy", new NoStrategy());

    c.refresh();
    
    c.getAutowireCapableBeanFactory().autowireBean(this);
    c.getAutowireCapableBeanFactory().autowireBean(instance);
    
    system.configure();
  }

}
