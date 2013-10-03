/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.mockwire.spring25;

import java.beans.Introspector;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import net.stickycode.component.spring25.InjectAnnotationBeanPostProcessor;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.spring25.ConfiguredBeanPostProcessor;
import net.stickycode.exception.PermanentException;
import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.MissingBeanException;
import net.stickycode.mockwire.NonUniqueBeanException;
import net.stickycode.stereotype.StickyComponent;

public class SpringIsolatedTestManifest
    implements IsolatedTestManifest {

  private GenericApplicationContext context;

  private Logger log = LoggerFactory.getLogger(getClass());

  public SpringIsolatedTestManifest() {
    super();

    context = new GenericApplicationContext();

    MockwireFieldInjectionAnnotationBeanPostProcessor blessInjector = new MockwireFieldInjectionAnnotationBeanPostProcessor();
    blessInjector.setBeanFactory(context.getDefaultListableBeanFactory());
    context.getBeanFactory().addBeanPostProcessor(blessInjector);

    InjectAnnotationBeanPostProcessor inject = new InjectAnnotationBeanPostProcessor();
    inject.setBeanFactory(context.getDefaultListableBeanFactory());
    context.getBeanFactory().addBeanPostProcessor(inject);

    CommonAnnotationBeanPostProcessor commonPostProcessor = new CommonAnnotationBeanPostProcessor();
    commonPostProcessor.setBeanFactory(context.getDefaultListableBeanFactory());
    context.getBeanFactory().addBeanPostProcessor(commonPostProcessor);

    context.getBeanFactory().registerSingleton(
        Introspector.decapitalize(getClass().getSimpleName()),
        this);
  }

  @Override
  public boolean hasRegisteredType(Class<?> type) {
    return context.getBeanNamesForType(type).length > 0;
  }

  @Override
  public void prepareTest(Object testInstance) {
    try {
      context.getAutowireCapableBeanFactory().autowireBean(testInstance);
    }
    catch (BeansException e) {
      Throwable cause = e.getMostSpecificCause();
      if (cause instanceof NoSuchBeanDefinitionException) {
        NoSuchBeanDefinitionException n = (NoSuchBeanDefinitionException) cause;
        throw new MissingBeanException(n, testInstance, n.getBeanType());
      }
      if (cause instanceof PermanentException)
        throw (PermanentException) cause;

      throw new TestInjectionFailure(e, testInstance.getClass());
    }
  }

  @Override
  public void registerBean(String beanName, Object bean, Class<?> type) {
    log.info("registering bean '{}' of type '{}'", beanName, type.getName());
    context.getBeanFactory().initializeBean(bean, beanName);
    context.getBeanFactory().registerSingleton(beanName, bean);
    // beans that get pushed straight into the context need to be attached to destructive bean post processors
    context.getDefaultListableBeanFactory().registerDisposableBean(
        beanName, new DisposableBeanAdapter(bean, beanName, context));
  }

  @Override
  public void registerType(String beanName, Class<?> type) {
    log.info("registering definition '{}' for type '{}'", beanName, type.getName());
    GenericBeanDefinition bd = new GenericBeanDefinition();
    bd.setBeanClass(type);
    bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    context.getDefaultListableBeanFactory().registerBeanDefinition(beanName, bd);
  }

  @Override
  public Object getBeanOfType(Class<?> type) {
    Map<String, ?> beans = context.getBeansOfType(type);
    if (beans.size() == 1)
      return beans.values().iterator().next();

    if (beans.size() == 0)
      throw new MissingBeanException(type);

    throw new NonUniqueBeanException(beans.size(), beans.keySet(), type);
  }

  @Override
  public void scanPackages(String[] scanRoots) {
    log.info("scanning roots {}", scanRoots);
    ClassPathBeanDefinitionScanner scanner = createScanner();
    XmlBeanDefinitionReader beanDefinitionReader = createXmlLoader();
    for (String s : scanRoots)
      if (s.endsWith(".xml"))
        beanDefinitionReader.loadBeanDefinitions(s);
      else
        scanner.scan(scanRoots);
  }

  private ClassPathBeanDefinitionScanner createScanner() {
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
    scanner.setIncludeAnnotationConfig(true);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    return scanner;
  }

  private XmlBeanDefinitionReader createXmlLoader() {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(context);
    beanDefinitionReader.setResourceLoader(context);
    beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(context));
    return beanDefinitionReader;
  }

  @Override
  public void registerConfigurationSystem(String name, Object configurationSystem, Class<?> type) {
    registerBean(name, configurationSystem, type);
    ConfiguredBeanPostProcessor configurer = new ConfiguredBeanPostProcessor();
    configurer.setConfiguration((ConfigurationSystem) configurationSystem);
    context.getBeanFactory().addBeanPostProcessor(configurer);
  }

  @Override
  public void startup(Class<?> testClass) {
    try {
      context.refresh();
    }
    catch (BeansException e) {
      Throwable cause = e.getMostSpecificCause();
      if (cause instanceof NoSuchBeanDefinitionException) {
        NoSuchBeanDefinitionException n = (NoSuchBeanDefinitionException) cause;
        throw new MissingBeanException(n, testClass, n.getBeanType());
      }
      if (cause instanceof PermanentException)
        throw (PermanentException) cause;

      throw new TestInjectionFailure(e, testClass);
    }
  }

  @Override
  public void shutdown() {
    context.close();
  }

  public GenericApplicationContext getContext() {
    return context;
  }

}
