package net.stickycode.configured.spring3.finder;

import java.beans.Introspector;

import net.stickycode.configured.finder.AbstractBeanFinderTest;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.configured.finder.TooMany;
import net.stickycode.configured.spring3.finder.SpringBeanFinder;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class BeanFinderTest
    extends AbstractBeanFinderTest {

  @Override
  protected BeanFinder getFinder() {
    GenericApplicationContext c = new GenericApplicationContext();
    createScanner(c).scan(
        AbstractBeanFinderTest.class.getPackage().getName(),
        SpringBeanFinder.class.getPackage().getName()
        );
    // registerSingleton(c, SingletonBean.class);
    // registerPrototype(c, Bean.class);
    // registerPrototypes(c, TooManyTwo.class, TooManyOne.class);
    // // registerPrototype(c, TooManyTwo.class);
    // registerSingleton(c, SpringBeanFinder.class);
    // wireit(c);
    c.refresh();
    return c.getBean(BeanFinder.class);
  }

  private ClassPathBeanDefinitionScanner createScanner(GenericApplicationContext c) {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(c);
    scanner.setIncludeAnnotationConfig(true);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    return scanner;
  }

  private void wireit(GenericApplicationContext c) {
    AutowiredAnnotationBeanPostProcessor p = new AutowiredAnnotationBeanPostProcessor();
    p.setBeanFactory(c.getBeanFactory());
    c.getDefaultListableBeanFactory().addBeanPostProcessor(p);
  }

  private void registerSingleton(GenericApplicationContext c, Class<?> type) {
    register(c, type, BeanDefinition.SCOPE_SINGLETON);
  }

  private void registerPrototype(GenericApplicationContext c, Class<?> type) {
    register(c, type, BeanDefinition.SCOPE_PROTOTYPE);
  }

  private void registerPrototypes(GenericApplicationContext c, Class<?> type, Class<?> type2) {
    RootBeanDefinition root = new RootBeanDefinition(TooMany.class);
    root.setAbstract(true);
    c.getDefaultListableBeanFactory().registerBeanDefinition(Introspector.decapitalize(TooMany.class.getSimpleName()), root);

    ChildBeanDefinition b = new ChildBeanDefinition(Introspector.decapitalize(TooMany.class.getSimpleName()));
    b.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
    b.setBeanClass(type);
    c.getDefaultListableBeanFactory().registerBeanDefinition(Introspector.decapitalize(type.getSimpleName()), b);

    ChildBeanDefinition d = new ChildBeanDefinition(Introspector.decapitalize(TooMany.class.getSimpleName()));
    d.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
    d.setBeanClass(type2);
    c.getDefaultListableBeanFactory().registerBeanDefinition(Introspector.decapitalize(type2.getSimpleName()), d);
  }

  private void register(GenericApplicationContext c, Class<?> type, String scopePrototype) {
    GenericBeanDefinition b = new GenericBeanDefinition();
    b.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
    b.setBeanClass(type);
    b.setScope(scopePrototype);
    c.getDefaultListableBeanFactory().registerBeanDefinition(Introspector.decapitalize(type.getSimpleName()), b);
  }

}
