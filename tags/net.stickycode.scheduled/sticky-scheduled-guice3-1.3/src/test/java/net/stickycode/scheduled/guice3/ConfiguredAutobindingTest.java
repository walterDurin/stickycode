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
import static org.mockito.Mockito.when;

import java.util.Collections;

import net.stickycode.bootstrap.guice3.StickyModule;
import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.guice3.ConfigurationSourceModule;

import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ConfiguredAutobindingTest
    extends AbstractScheduledComponentTest {

  @Override
  protected void configure(ScheduleTestObject instance) {
    Injector injector = Guice.createInjector(
        StickyModule.bootstrapModule(create("net.stickycode")),
        StickyModule.keyBuilderModule())
        .createChildInjector(
            StickyModule.applicationModule(create("net.stickycode")),
            configurationSourceModule());
    injector.injectMembers(instance);
    injector.injectMembers(this);
  }

  private Module configurationSourceModule() {
    ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
    when(configurationSource.hasValue("scheduleTestObject.runIt.schedule")).thenReturn(true);
    when(configurationSource.getValue("scheduleTestObject.runIt.schedule")).thenReturn("every 2 seconds");
    return new ConfigurationSourceModule(Collections.singletonList(configurationSource));
  }

}
