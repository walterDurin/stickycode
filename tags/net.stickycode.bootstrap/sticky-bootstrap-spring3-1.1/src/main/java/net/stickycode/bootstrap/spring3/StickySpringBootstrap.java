package net.stickycode.bootstrap.spring3;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class StickySpringBootstrap {

  private GenericApplicationContext context;

  public StickySpringBootstrap(GenericApplicationContext context) {
    super();
    this.context = context;
  }

  public void scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.scan(paths);
  }

}
