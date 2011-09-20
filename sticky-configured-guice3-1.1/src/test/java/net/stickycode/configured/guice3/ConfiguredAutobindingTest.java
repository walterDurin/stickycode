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
package net.stickycode.configured.guice3;

import static org.mockito.Mockito.when;

import java.util.Collections;

import net.stickycode.configured.AbstractConfiguredComponentTest;
import net.stickycode.configured.ConfigurationSource;

import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import de.devsurf.injection.guice.scanner.PackageFilter;

public class ConfiguredAutobindingTest
    extends AbstractConfiguredComponentTest {

  @Override
  protected void configure(ConfiguredTestObject target) {
    PackageFilter packageFilter = PackageFilter.create("net.stickycode");
    Module startup = StickyModule.bootstrapModule(packageFilter);
    Injector injector = Guice.createInjector(startup, StickyModule.keyBuilderModule(), configurationSourceModule());
    injector.injectMembers(target);
    injector.injectMembers(this);
  }

  private Module configurationSourceModule() {
    ConfigurationSource configurationSource = Mockito.mock(ConfigurationSource.class);
    when(configurationSource.hasValue("configuredTestObject.bob")).thenReturn(true);
    when(configurationSource.hasValue("configuredTestObject.numbers")).thenReturn(true);
    when(configurationSource.getValue("configuredTestObject.bob")).thenReturn("yay");
    when(configurationSource.getValue("configuredTestObject.numbers")).thenReturn("1,5,3,7");
    return new ConfigurationSourceModule(Collections.singletonList(configurationSource));
  }


}
