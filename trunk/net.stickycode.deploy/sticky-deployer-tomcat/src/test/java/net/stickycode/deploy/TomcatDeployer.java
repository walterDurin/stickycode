/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
package net.stickycode.deploy;

import java.io.File;

import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Embedded;

public class TomcatDeployer {

  private Embedded container;
  private Engine engine;
  private StandardHost host;

  public void deploy(File warPath, int port) {

    createContainer();
    createEngine();
    createDefaultHost();
    createContextForWar(warPath);
    listenToHttpOnPort(port);

    try {
      container.start();
    }
    catch (LifecycleException e) {
      throw new FailedToStartDeploymentException(e);
    }
  }

  private void listenToHttpOnPort(int port) {
    Connector connector = container.createConnector("localhost", port, "http");
    container.addConnector(connector);
  }

  private void createContextForWar(File warPath) {
    StandardContext context = new StandardContext();
    context.setDocBase(warPath.getAbsolutePath());
    context.setPath("");
    context.setLoader(new WebappLoader());
    context.setProcessTlds(false);
    context.setTldNamespaceAware(false);
    context.setAntiResourceLocking(false);

    ContextConfig listener = new ContextConfig();
    listener.setDefaultWebXml("META-INF/sticky/stripped-web.xml");
    context.addLifecycleListener(listener);

    host.addChild(context);
  }

  private void createDefaultHost() {
    host = new StandardHost();
    host.setName("sticky-host");
    host.setUnpackWARs(false);
    host.setName("sticky-host");
    engine.addChild(host);
    engine.setDefaultHost(host.getName());
  }

  private void createEngine() {
    engine = container.createEngine();
    engine.setName("sticky-" + System.currentTimeMillis());
    container.addEngine(engine);
  }

  public void stop() {
    try {
      container.stop();
    }
    catch (LifecycleException e) {
      throw new FailedToStopDeploymentException(e);
    }
  }

  private void createContainer() {
    Embedded e = new Embedded();
    e.setName("sticky-container");
    e.setUseNaming(true);
    e.setCatalinaHome("target-eclipse");
    container = e;
  }
}