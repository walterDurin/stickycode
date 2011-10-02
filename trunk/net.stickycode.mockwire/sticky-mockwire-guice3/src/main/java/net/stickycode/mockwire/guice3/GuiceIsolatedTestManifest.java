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

import static net.stickycode.configured.guice3.StickyModule.bootstrapModule;
import static net.stickycode.configured.guice3.StickyModule.keyBuilderModule;

import java.util.ArrayList;
import java.util.List;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.guice3.ConfigurationSourceModule;
import net.stickycode.configured.guice3.StickyModule;
import net.stickycode.guice3.jsr250.Jsr250Module;
import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.MissingBeanException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import de.devsurf.injection.guice.scanner.PackageFilter;

public class GuiceIsolatedTestManifest
    implements IsolatedTestManifest {

  Logger log = LoggerFactory.getLogger(getClass());

  private IsolatedTestModuleMetadata manifest = new IsolatedTestModuleMetadata();

  private Injector injector;

  private List<PackageFilter> packageFilters;

  private List<ConfigurationSource> configurationSources;

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
    if (packageFilters == null)
      packageFilters = new ArrayList<PackageFilter>();

    for (int i = 0; i < scanRoots.length; i++) {
      packageFilters.add(PackageFilter.create(scanRoots[i]));
    }
  }

  @Override
  public void prepareTest(Object testInstance) throws MissingBeanException {
    // this should be in start up but we can't startup guice without the instance...
    IsolatedTestModule isolatedTestModule = new IsolatedTestModule(testInstance.getClass(), manifest);
    if (packageFilters == null)
      injector = Guice.createInjector(isolatedTestModule, new ConfigurationSourceModule(configurationSources));
    else
      injector = bootstrap(isolatedTestModule);

    injector.injectMembers(testInstance);
  }

  private Injector bootstrap(IsolatedTestModule isolatedTestModule) {
    PackageFilter[] filters = packageFilters.toArray(new PackageFilter[packageFilters.size()]);
    return Guice.createInjector(Stage.PRODUCTION,
        bootstrapModule(filters),
        keyBuilderModule()
        )
        .createChildInjector(
            isolatedTestModule,
            StickyModule.applicationModule(filters),
            new ConfigurationSourceModule(configurationSources));
  }

  @Override
  public void shutdown() {
    Jsr250Module.preDestroy(log, injector);
  }

  @Override
  public void startup(Class<?> testClass) {

  }

  @Override
  public void registerConfiguationSystem(List<ConfigurationSource> configurationSources) {
    this.configurationSources = configurationSources;
    if (packageFilters == null)
      packageFilters = new ArrayList<PackageFilter>();
     packageFilters.add(PackageFilter.create("net.stickycode.coercion"));
     packageFilters.add(PackageFilter.create("net.stickycode.configured"));
     packageFilters.add(PackageFilter.create("net.stickycode.guice3"));
  }

  @Override
  public void configure() {
    injector.getInstance(ConfigurationSystem.class).configure();
  }

}
