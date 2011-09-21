/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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

import static de.devsurf.injection.guice.scanner.PackageFilter.create;
import static net.stickycode.configured.guice3.StickyModule.bootstrapModule;
import static net.stickycode.configured.guice3.StickyModule.keyBuilderModule;
import static org.mockito.Mockito.when;

import java.util.Collections;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.guice3.ConfigurationSourceModule;
import net.stickycode.scheduled.SchedulingSystem;

import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ConfiguredAutobindingTest
    extends AbstractScheduledComponentTest {

  @Override
  protected void configure(ScheduleTestObject instance) {
    Injector injector = Guice.createInjector(
        bootstrapModule(create("net.stickycode")), 
        keyBuilderModule(),
        schedulingSystemModule(),
        configurationSourceModule());
    injector.injectMembers(instance);
    injector.injectMembers(this);
  }

  private Module schedulingSystemModule() {
    return new AbstractModule() {

      @Override
      protected void configure() {
        bind(SchedulingSystem.class).toInstance(Mockito.mock(SchedulingSystem.class));
      }
    };
  }

  private Module configurationSourceModule() {
    ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
    when(configurationSource.hasValue("scheduleTestObject.runIt")).thenReturn(true);
    when(configurationSource.getValue("scheduleTestObject.runIt")).thenReturn("every 2 seconds");
    return new ConfigurationSourceModule(Collections.singletonList(configurationSource));
  }

}
