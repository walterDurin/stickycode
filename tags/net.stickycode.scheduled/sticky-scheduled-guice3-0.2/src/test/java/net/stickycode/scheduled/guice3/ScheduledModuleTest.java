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
package net.stickycode.scheduled.guice3;

import static org.mockito.Mockito.when;

import java.util.Collections;

import net.stickycode.coercion.Coercion;
import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.guice3.ConfigurationSourceModule;
import net.stickycode.configured.guice3.ConfiguredModule;
import net.stickycode.scheduled.SchedulingSystem;
import net.stickycode.scheduled.configuration.ScheduleConfigurationCoercion;

import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ScheduledModuleTest
    extends AbstractScheduledComponentTest {

  @Override
  protected void configure(ScheduleTestObject instance) {
    Class<? extends Coercion> klass = ScheduleConfigurationCoercion.class;
    Injector parent = Guice.createInjector(configurationSourceModule(), new ConfiguredModule().withCoercion(klass));
    Injector injector = parent.createChildInjector(new ScheduledModule(), schedulingSystemModule());
    injector.injectMembers(instance);
    injector.injectMembers(this);
  }

  private Module schedulingSystemModule() {
    final SchedulingSystem system = Mockito.mock(SchedulingSystem.class);
    return new AbstractModule() {

      @Override
      protected void configure() {
        bind(SchedulingSystem.class).toInstance(system);
      }
    };
  }

  private Module configurationSourceModule() {
    ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
    when(configurationSource.hasValue("scheduleTestObject.runit")).thenReturn(true);
    when(configurationSource.getValue("scheduleTestObject.runit")).thenReturn("every 2 seconds");
    return new ConfigurationSourceModule(Collections.singletonList(configurationSource));
  }

}
