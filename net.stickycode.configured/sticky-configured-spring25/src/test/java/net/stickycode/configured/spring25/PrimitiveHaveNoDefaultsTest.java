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
package net.stickycode.configured.spring25;

import java.beans.Introspector;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import net.stickycode.coercion.Coercions;
import net.stickycode.coercion.PatternCoercion;
import net.stickycode.component.spring25.InjectAnnotationBeanPostProcessor;
import net.stickycode.configured.AbstractPrimitiveConfiguratedTest;
import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.InlineConfigurationRepository;

public class PrimitiveHaveNoDefaultsTest
    extends AbstractPrimitiveConfiguratedTest {

//  @Override
//  protected void configure(Object target, ConfigurationSource configurationSource) {
//    ConfigurationSystem system = new ConfigurationSystem(configurationSource);
//    ConfiguredBeanPostProcessor processor = new ConfiguredBeanPostProcessor();
//    processor.setConfiguration(system);
//    processor.postProcessAfterInstantiation(target, "configured");
//    system.configure();
//  }
  protected void configure(Object instance, ConfigurationSource configurationSource) {
    GenericApplicationContext c = new GenericApplicationContext();
    c.getBeanFactory().registerSingleton(name(ConfigurationSource.class), configurationSource);

    registerType(c, InjectAnnotationBeanPostProcessor.class);
    registerType(c, InlineConfigurationRepository.class);
    registerType(c, ConfigurationSystem.class);
    registerType(c, ConfiguredBeanPostProcessor.class);
    registerType(c, PatternCoercion.class);
    registerType(c, Coercions.class);

    c.refresh();

    c.getAutowireCapableBeanFactory().autowireBean(instance);
    c.getAutowireCapableBeanFactory().autowireBean(this);
  }

  public void registerType(GenericApplicationContext c, Class<?> type) {
    GenericBeanDefinition bd = new GenericBeanDefinition();
    bd.setBeanClass(type);
    bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    c.getDefaultListableBeanFactory().registerBeanDefinition(name(type), bd);
  }

  private String name(Class<?> type) {
    return Introspector.decapitalize(type.getSimpleName());
  }

}
