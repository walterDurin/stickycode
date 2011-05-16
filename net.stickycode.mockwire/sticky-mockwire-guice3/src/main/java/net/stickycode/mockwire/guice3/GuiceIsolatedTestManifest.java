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
package net.stickycode.mockwire.guice3;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.guice3.ConfigurationSourceModule;
import net.stickycode.configured.guice3.ConfiguredModule;
import net.stickycode.guice3.jsr250.Jsr250Module;
import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.MissingBeanException;

public class GuiceIsolatedTestManifest
    implements IsolatedTestManifest {

  Logger log = LoggerFactory.getLogger(getClass());

  private IsolatedTestModuleMetadata manifest = new IsolatedTestModuleMetadata();
  private Injector injector;
  private Injector configurationInjector;

  public GuiceIsolatedTestManifest() {
    manifest.registerBean("manifest", this, IsolatedTestManifest.class);
  }

  @Override
  public boolean hasRegisteredType(Class<?> type) {
    return manifest.hasRegisteredType(type);
  }

  @Override
  public void registerBean(String beanName, Object bean, Class<?> type) {
    log.debug("register bean '{}' to instance of '{}'", beanName, type.getName());
    manifest.registerBean(beanName, bean, type);
  }

  @Override
  public void registerType(String beanName, Class<?> type) {
    log.debug("register name '{}' to type '{}'", beanName, type.getName());
    manifest.registerType(beanName, type);
  }

  @Override
  public <T> T getBeanOfType(Class<T> type) {
    return manifest.getBean(type);
  }

  @Override
  public void scanPackages(String[] scanRoots) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void prepareTest(Object testInstance) throws MissingBeanException {
    IsolatedTestModule isolatedTestModule = new IsolatedTestModule(testInstance.getClass(), manifest);
    if (configurationInjector == null) {
      injector = Guice.createInjector(isolatedTestModule);
    }
    else
      injector = configurationInjector.createChildInjector(isolatedTestModule);

    injector.injectMembers(testInstance);
  }

  @Override
  public void shutdown() {
    Jsr250Module.preDestroy(injector);
  }

  @Override
  public void startup(Class<?> testClass) {
  }

  @Override
  public void registerConfiguationSystem(List<ConfigurationSource> configurationSources) {
    configurationInjector = Guice.createInjector(
        new Jsr250Module(),
        new ConfigurationSourceModule(configurationSources),
        new ConfiguredModule());
  }

  @Override
  public void configure() {
    injector.getInstance(ConfigurationSystem.class).configure();
  }

}
