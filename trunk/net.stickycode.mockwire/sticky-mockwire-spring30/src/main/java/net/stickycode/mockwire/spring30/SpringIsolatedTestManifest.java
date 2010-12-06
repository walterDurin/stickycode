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
package net.stickycode.mockwire.spring30;

import java.beans.Introspector;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import net.stickycode.configured.spring25.ConfiguredBeanPostProcessor;
import net.stickycode.exception.PermanentException;
import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.MissingBeanException;
import net.stickycode.mockwire.NonUniqueBeanException;
import net.stickycode.stereotype.StickyComponent;

public class SpringIsolatedTestManifest
    extends GenericApplicationContext
    implements IsolatedTestManifest {

  private Logger log = LoggerFactory.getLogger(getClass());

  public SpringIsolatedTestManifest() {
    super();

    MockwireFieldInjectionAnnotationBeanPostProcessor blessInjector = new MockwireFieldInjectionAnnotationBeanPostProcessor();
    blessInjector.setBeanFactory(getDefaultListableBeanFactory());
    getBeanFactory().addBeanPostProcessor(blessInjector);

    getBeanFactory().registerSingleton(
        Introspector.decapitalize(getClass().getSimpleName()),
        this);
  }

  public SpringIsolatedTestManifest(ApplicationContext parent) {
    super(parent);
  }

  public SpringIsolatedTestManifest(DefaultListableBeanFactory beanFactory, ApplicationContext parent) {
    super(beanFactory, parent);
  }

  public SpringIsolatedTestManifest(DefaultListableBeanFactory beanFactory) {
    super(beanFactory);
  }

  @Override
  public boolean hasRegisteredType(Class<?> type) {
    return getBeanNamesForType(type).length > 0;
  }

  @Override
  public void autowire(Object testInstance) {
    try {
      refresh();
      getAutowireCapableBeanFactory().autowireBean(testInstance);
    }
    catch (BeansException e) {
      Throwable cause = e.getMostSpecificCause();
      if (cause instanceof NoSuchBeanDefinitionException) {
        NoSuchBeanDefinitionException n = (NoSuchBeanDefinitionException)cause;
        throw new MissingBeanException(n, testInstance, n.getBeanType());
      }
      if (cause instanceof PermanentException)
        throw (PermanentException)cause;

      throw new TestInjectionFailure(e, testInstance);
    }
  }



  @Override
  public void registerBean(String beanName, Object bean, Class<?> type) {
    log.info("registering bean '{}' of type '{}'", beanName, type.getName());
    getBeanFactory().initializeBean(bean, beanName);
    getBeanFactory().registerSingleton(beanName, bean);
    // beans that get pushed straight into the context need to be attached to destructive bean post processors
    getDefaultListableBeanFactory().registerDisposableBean(
        beanName, new DisposableBeanAdapter(bean, beanName, this));
  }

  @Override
  public void registerType(String beanName, Class<?> type) {
    log.info("registering definition '{}' for type '{}'", beanName, type.getName());
    GenericBeanDefinition bd = new GenericBeanDefinition();
    bd.setBeanClass(type);
    bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    getDefaultListableBeanFactory().registerBeanDefinition(beanName, bd);
  }

  @Override
  public Object getBeanOfType(Class<?> type) {
    Map<String, ?> beans = getBeansOfType(type);
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
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this);
    scanner.setIncludeAnnotationConfig(true);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    return scanner;
  }

  private XmlBeanDefinitionReader createXmlLoader() {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this);
    beanDefinitionReader.setResourceLoader(this);
    beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
    return beanDefinitionReader;
  }

  @Override
  public void registerConfigurationSystem(String name, Object configurationSystem, Class<?> type) {
    registerBean(name, configurationSystem, type);
    registerType(
        Introspector.decapitalize(ConfiguredBeanPostProcessor.class.getSimpleName()),
        ConfiguredBeanPostProcessor.class);
  }
}
