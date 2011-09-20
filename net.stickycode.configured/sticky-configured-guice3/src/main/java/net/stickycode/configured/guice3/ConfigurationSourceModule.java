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

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import net.stickycode.configured.ConfigurationSource;

public class ConfigurationSourceModule
    extends AbstractModule {

  private final List<ConfigurationSource> configurationSources;

  public ConfigurationSourceModule(List<ConfigurationSource> configurationSource) {
    this.configurationSources = configurationSource;
  }

  @Override
  protected void configure() {
    if (configurationSources == null)
      return;
    
    Multibinder<ConfigurationSource> sources = Multibinder.newSetBinder(binder(), ConfigurationSource.class);
    for (ConfigurationSource source : configurationSources) {
      sources.addBinding().toInstance(source);
    }
  }
}
