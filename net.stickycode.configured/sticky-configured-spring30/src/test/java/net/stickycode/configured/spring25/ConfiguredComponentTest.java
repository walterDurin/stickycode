/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.mockito.Mockito.when;

import static org.fest.assertions.Assertions.assertThat;
public class ConfiguredComponentTest {

  public class ConfiguredTestObject {

    @Configured
    String bob;

    @Configured
    List<Integer> numbers;
  }

  @Test
  public void configured() {
    ConfiguredTestObject instance = new ConfiguredTestObject();
    configure(instance);
    assertThat(instance.bob)
        .describedAs("Configured field was not set")
        .isNotNull();

    assertThat(instance.numbers).containsExactly(1, 3, 5, 7);
  }

  private void configure(Object instance) {
    GenericApplicationContext c = new GenericApplicationContext();
    ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
    when(configurationSource.hasValue("configuredTestObject.bob")).thenReturn(true);
    when(configurationSource.hasValue("configuredTestObject.numbers")).thenReturn(true);
    when(configurationSource.getValue("configuredTestObject.bob")).thenReturn("yay");
    when(configurationSource.getValue("configuredTestObject.numbers")).thenReturn("1,3,5,7");
    c.getBeanFactory().registerSingleton(name(ConfigurationSource.class), configurationSource);

    registerType(c, ConfigurationSystem.class);
    registerType(c, ConfiguredBeanPostProcessor.class);
    registerType(c, AutowiredAnnotationBeanPostProcessor.class);

    c.refresh();

    c.getAutowireCapableBeanFactory().autowireBean(instance);
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
