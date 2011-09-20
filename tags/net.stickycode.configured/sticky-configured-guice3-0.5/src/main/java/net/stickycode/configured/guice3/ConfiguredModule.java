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

import java.util.ArrayList;
import java.util.List;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.Coercions;
import net.stickycode.coercion.PatternCoercion;
import net.stickycode.configured.ConfigurationKeyBuilder;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.InlineConfigurationRepository;
import net.stickycode.configured.SimpleNameDotFieldConfigurationKeyBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;

public class ConfiguredModule
    extends AbstractModule {

  private List<Class<? extends Coercion>> coercions = new ArrayList<Class<? extends Coercion>>();

  public ConfiguredModule() {
    coercions.add(PatternCoercion.class);
  }

  public ConfiguredModule withCoercion(Class<? extends Coercion> coercion) {
    coercions.add(coercion);
    return this;
  }

  @Override
  protected void configure() {
    bind(ConfigurationSystem.class).in(Singleton.class);
    bind(ConfiguredInjector.class).in(Singleton.class);
    bind(ConfigurationKeyBuilder.class).to(SimpleNameDotFieldConfigurationKeyBuilder.class).in(Singleton.class);
    bind(ConfigurationRepository.class).to(InlineConfigurationRepository.class).in(Singleton.class);
    bindCoercions();
    ConfiguredTypeListener listener = new ConfiguredTypeListener();
    requestInjection(listener);
    bindListener(Matchers.any(), listener);
  }

  // XXX So coercion needs to be '' not '<?>' as there is no way that I could figure
  // out how to actually get guice to multibind to a set of Coercion<?>
  private void bindCoercions() {
    // TypeLiteral<Coercion<?>> t = new CoercionTypeLiteral();
    TypeLiteral<Coercion> type = TypeLiteral.get(Coercion.class);
    Multibinder<Coercion> extensions = Multibinder.newSetBinder(binder(), type);
    for (Class<? extends Coercion> coercion : coercions) {
      extensions.addBinding().to(coercion);
    }
    bind(CoercionFinder.class).to(Coercions.class);
  }

}
