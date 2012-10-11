/**
 * Copyright (c) 2012 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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
package net.stickycode.servlet.spring3;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.bootstrap.spring3.StickySpringBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

@WebListener
public class StickySpringContextListener
    extends ContextLoaderListener {

  private Logger log = LoggerFactory.getLogger(StickySpringContextListener.class);

  @Inject
  private StickyBootstrap system;

  @Override
  protected WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
    GenericWebApplicationContext context = new GenericWebApplicationContext(servletContext);

    StickySpringBootstrap bootstrap = new StickySpringBootstrap(context);
    bootstrap.scan("net.stickycode");

    String packageList = servletContext.getInitParameter("sticky-application-packages");
    if (packageList == null) {
      log.warn("Only scanning from root net.stickycode. Set sticky-application-package to include the other scan roots for your application");
    }
    else {
      bootstrap.scan(packageList.split(","));
    }

    return context;
  }

  @Override
  protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
    super.configureAndRefreshWebApplicationContext(wac, sc);
    wac.getAutowireCapableBeanFactory().autowireBean(this);
  }

  @Override
  public void contextInitialized(ServletContextEvent event) {
    super.contextInitialized(event);
    system.start();
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    try {
      system.shutdown();
    }
    finally {
      super.contextDestroyed(event);
    }
  }

}
