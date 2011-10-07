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
package net.stickycode.guice3;

import java.util.logging.LogManager;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.guice3.StickyModule;
import net.stickycode.cxf.guice3.StickyCxfServlet;
import net.stickycode.guice3.jsr250.Jsr250Module;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

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
  ConfigurationSystem configuration;

  @Override
  protected Injector getInjector() {
    log.info("building injector");
    Injector injector = Guice.createInjector(Stage.PRODUCTION,
        StickyModule.bootstrapModule(
            PackageFilter.create("net.stickycode")),
        StickyModule.keyBuilderModule(), cxfModule())
        .createChildInjector(
            StickyModule.applicationModule(
                PackageFilter.create("net.stickycode")),
            servletModule()
        );
    injector.injectMembers(this);

    return injector;
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    final ServletContext servletContext = servletContextEvent.getServletContext();

    Injector injector = getInjector();
    servletContext.setAttribute(Injector.class.getName(), injector);
    configure();
  }

  void configure() {
    log.info("configuring");
    configuration.configure();
    log.info("configured");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    Injector injector = (Injector) servletContextEvent.getServletContext().getAttribute(Injector.class.getName());
    shutdown(injector);
    super.contextDestroyed(servletContextEvent);
  }

  void shutdown(Injector injector) {
     Jsr250Module.preDestroy(log, injector);
  }

  private Module cxfModule() {
    return new AbstractModule() {

      @Override
      protected void configure() {
        bind(StickyCxfServlet.class).asEagerSingleton();
        bind(Bus.class).toProvider(new Provider<Bus>() {

          @Override
          public Bus get() {
            return BusFactory.newInstance().createBus();
          }
        }).asEagerSingleton();
      }
    };
  }

  private ServletModule servletModule() {
    return new ServletModule() {

      @Override
      protected void configureServlets() {
        serve("/services/*").with(StickyCxfServlet.class);
        serve("/*").with(StickyCxfServlet.class);
      }
    };
  }
  
}
