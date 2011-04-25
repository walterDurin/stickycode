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
package net.stickycode.configured.guice3;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ConfiguredModuleTest {

  public class ConfiguredTestObject {
    @Configured
    String bob;

    @Configured
    List<Integer> numbers;
  }

  @Test
  public void first() {
    ConfiguredTestObject instance = new ConfiguredTestObject();
    ConfigurationSystem configuration = configure(instance);
    assertThat(instance.bob).as("Injector should have configured a value").isNotNull();
    assertThat(instance.bob).isEqualTo("yay");
    assertThat(instance.numbers).isNotNull();
    assertThat(instance.numbers).containsExactly(1,5,3,7);

    assertThat(configuration.registeredFieldCount()).isEqualTo(2);
  }

  protected ConfigurationSystem configure(ConfiguredTestObject instance) {
    Injector injector = Guice.createInjector(configurationSourceModule(), new ConfiguredModule());
    injector.injectMembers(instance);
    return injector.getInstance(ConfigurationSystem.class);
  }

  private Module configurationSourceModule() {
    return new AbstractModule() {

      @Override
      protected void configure() {
        Multibinder<ConfigurationSource> sources = Multibinder.newSetBinder(binder(), ConfigurationSource.class);
        ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
        when(configurationSource.hasValue("configuredTestObject.bob")).thenReturn(true);
        when(configurationSource.hasValue("configuredTestObject.numbers")).thenReturn(true);
        when(configurationSource.getValue("configuredTestObject.bob")).thenReturn("yay");
        when(configurationSource.getValue("configuredTestObject.numbers")).thenReturn("1,5,3,7");
        sources.addBinding().toInstance(configurationSource);
      }
    };
  }

}
