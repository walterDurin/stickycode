package net.stickycode.bootstrap.spring3;

import java.awt.Component;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

public class SpringStickyBootstrap
    implements StickyBootstrap {

  private GenericApplicationContext context;

  public SpringStickyBootstrap() {
    this.context = new GenericApplicationContext();
    scan("net.stickycode");
  }

  public SpringStickyBootstrap(GenericApplicationContext context) {
    this.context = context;
    scan("net.stickycode");
  }

  public SpringStickyBootstrap(String... paths) {
    this(new GenericApplicationContext());
    if (paths != null && paths.length > 0)
      scan(paths);
  }

  public StickyBootstrap scan(String... paths) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
    scanner.setScopeMetadataResolver(new StickyScopeMetadataResolver());
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(org.springframework.stereotype.Component.class));
    scanner.scan(paths);
    return this;
  }

  public AutowireCapableBeanFactory getAutowirer() {
    if (!context.isActive())
      context.refresh();

    return context.getAutowireCapableBeanFactory();
  }

  @Override
  public void inject(Object value) {
    getAutowirer().autowireBean(value);
  }

  @Override
  public <T> T find(Class<T> type) {
    return context.getBean(type);
  }

}
