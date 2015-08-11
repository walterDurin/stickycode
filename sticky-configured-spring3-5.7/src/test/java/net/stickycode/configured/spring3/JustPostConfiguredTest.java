package net.stickycode.configured.spring3;

import net.stickycode.configured.AbstractJustPostConfiguredTest;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class JustPostConfiguredTest
    extends AbstractJustPostConfiguredTest {

  public void configured(Object target) {
    GenericApplicationContext c = new GenericApplicationContext();

    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(
        c, false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.scan("net.stickycode");

    c.refresh();

    c.getAutowireCapableBeanFactory().autowireBean(this);
    c.getAutowireCapableBeanFactory().autowireBean(target);
  }
}
