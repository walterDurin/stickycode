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
package net.stickycode.mockwire.guice2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;

public class IsolatedTestModule
    extends AbstractModule {

  private Logger log = LoggerFactory.getLogger(getClass());

  private final IsolatedTestModuleMetadata manifest;
  private final Class<?> testClass;

  public IsolatedTestModule(Class<?> testClass, IsolatedTestModuleMetadata manifest) {
    this.manifest = manifest;
    this.testClass = testClass;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void configure() {
    bindListener(Matchers.any(), new TestTypeListener(testClass));

    for (final BeanHolder b : manifest.getBeans()) {
      TypeLiteral type = TypeLiteral.get(b.getType());
      log.debug("binding type '{}' to instance '{}'", type, b.getInstance());
      bind(type).toInstance(b.getInstance());
    }
    for (Class<?> type : manifest.getTypes()) {
      log.debug("binding type '{}'", type);
      bind(type).in(Singleton.class);
    }

    for (Module  module: manifest.getModules()) {
      log.debug("installing module '{}'", module);
      install(module);
    }
  }

}
