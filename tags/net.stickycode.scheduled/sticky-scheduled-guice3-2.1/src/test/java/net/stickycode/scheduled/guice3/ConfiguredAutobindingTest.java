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
import net.stickycode.bootstrap.guice3.StickyModule;
import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;

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
    return new AbstractModule() {

      @Override
      protected void configure() {
        ConfigurationSource configurationSource = new ConfigurationSource() {

          @Override
          public void apply(ConfigurationKey key, ResolvedConfiguration values) {
            if (key.join(".").equals("scheduleTestObject.runIt.schedule"))
              values.add(new ConfigurationValue() {

                @Override
                public boolean hasPrecedence(ConfigurationValue v) {
                  return false;
                }

                @Override
                public String get() {
                  return "every 2 seconds";
                }
              });
          }
        };

        Multibinder<ConfigurationSource> sources = Multibinder.newSetBinder(binder(), ConfigurationSource.class);
        sources.addBinding().toInstance(configurationSource);
      }
    };
  }

}
