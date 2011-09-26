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

import net.stickycode.configured.ConfigurationKeyBuilder;
import net.stickycode.configured.SimpleNameDotFieldConfigurationKeyBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;

import de.devsurf.injection.guice.scanner.PackageFilter;
import de.devsurf.injection.guice.scanner.StartupModule;
import de.devsurf.injection.guice.scanner.asm.ASMClasspathScanner;

public class StickyModule {

  static public Module bootstrapModule(PackageFilter... packageFilter) {
    return StartupModule
        .create(ASMClasspathScanner.class, packageFilter)
        .addFeature(StickyFrameworkStereotypeScannerFeature.class)
        .addFeature(StickyFrameworkPluginMultibindingFeature.class)
        .disableStartupConfiguration();
  }

  static public Module applicationModule(PackageFilter... packageFilter) {
    return StartupModule
        .create(ASMClasspathScanner.class, packageFilter)
        .addFeature(StickyStereotypeScannerFeature.class)
        .addFeature(StickyPluginMultibindingFeature.class)
        .disableStartupConfiguration();
  }

  static public Module keyBuilderModule() {
    return new AbstractModule() {

      @Override
      protected void configure() {
        bind(ConfigurationKeyBuilder.class).to(SimpleNameDotFieldConfigurationKeyBuilder.class).in(Singleton.class);
      }
    };
  }

}
