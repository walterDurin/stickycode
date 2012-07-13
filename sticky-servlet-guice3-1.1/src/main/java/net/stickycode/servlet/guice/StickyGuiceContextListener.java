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
package net.stickycode.servlet.guice;

import java.util.Set;
import java.util.logging.LogManager;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import net.stickycode.bootstrap.StickySystem;
import net.stickycode.bootstrap.guice3.StickyModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Stage;
import com.google.inject.servlet.ExplicitBindingsFixitModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.util.Types;

import de.devsurf.injection.guice.scanner.PackageFilter;

public class StickyGuiceContextListener
    extends GuiceServletContextListener {

  static {
    java.util.logging.Logger util = LogManager.getLogManager().getLogger("");
    for (java.util.logging.Handler handler : util.getHandlers())
      util.removeHandler(handler);
    SLF4JBridgeHandler.install();
  }

  private Logger log = LoggerFactory.getLogger(StickyGuiceContextListener.class);

  @Inject
  private StickySystem system;

  @Inject
  private Injector injector;

  private PackageFilter[] packageFilters;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    ServletContext servletContext = servletContextEvent.getServletContext();
    // save the packages to scan from servlet init params
    // so we can create the injector from them
    // it seems a bit short sighted that the getInjector template method does not pass in the context
    initialisePackagesToScan(servletContext);
    super.contextInitialized(servletContextEvent);
    
    initialise();  
  }

  private void initialise() {
    injector.injectMembers(this);
    system.start();
  }

  @Override
  protected Injector getInjector() {
    return getInjector(packageFilters);
  }

  protected Injector getInjector(PackageFilter[] packageFilters) {
    log.info("building injector");
    Injector parent = Guice.createInjector(Stage.PRODUCTION,
        StickyModule.bootstrapModule(
            PackageFilter.create("net.stickycode")),
        StickyModule.keyBuilderModule())
        .createChildInjector(
            StickyModule.applicationModule(packageFilters),
            new ExplicitBindingsFixitModule());

    @SuppressWarnings("unchecked")
    Key<Set<StickyGuicePlugin>> key = (Key<Set<StickyGuicePlugin>>) Key.get(Types.setOf(StickyGuicePlugin.class));
    Set<StickyGuicePlugin> plugins = parent.getInstance(key);

    return parent.createChildInjector(servletModule(plugins));
  }

  protected void initialisePackagesToScan(ServletContext servletContext) {
    packageFilters = createApplicationPackage(servletContext);
  }

  protected PackageFilter[] createApplicationPackage(ServletContext servletContext) {
    String packageList = servletContext.getInitParameter("sticky-application-packages");
    if (packageList == null) {
      log.warn("Only scanning from root net.stickycode. Set sticky-application-package to include the other scan roots for your application");
      return new PackageFilter[] { PackageFilter.create("net.stickycode") };
    }

    String[] packages = packageList.split(",");
    log.info("scanning for these roots {} for components", packages);
    PackageFilter[] filters = new PackageFilter[packages.length + 1];
    filters[0] = PackageFilter.create("net.stickycode");
    for (int i = 0; i < packages.length; i++) {
      filters[i + 1] = PackageFilter.create(packages[i]);
    }
    return filters;
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    super.contextDestroyed(servletContextEvent);
    system.shutdown();
  }

//  void shutdown(Injector injector) {
//    Jsr250Module.preDestroy(log, injector);
//  }

  protected ServletModule servletModule(final Set<StickyGuicePlugin> plugins) {
    return new ServletModule() {
      @Override
      protected void configureServlets() {
        for (StickyGuicePlugin plugin : plugins) {
          log.info("loading {}", plugin);
          plugin.bind(binder());
          for (ServletMapping mapping : plugin.getServletMappings())
            serve(mapping.getPath()).with(mapping.getServlet());
        }
       
      }
    };
  }

}
