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
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import net.stickycode.deploy.bootstrap.StickyBootstrap;
import net.stickycode.deploy.bootstrap.StickyJar;


public class Deploy {
  public static void main(String[] args) throws ClassNotFoundException, IOException {

    URL url = ClassLoader.getSystemResource("RED-INF/lib/sticky-exception-1.3.jar");
    JarInputStream i = new JarInputStream(url.openStream());
    JarEntry current = i.getNextJarEntry();
    while (current != null) {
      if (!current.isDirectory())
        System.out.println(current.getName());
      current = i.getNextJarEntry();
    }

    File zip = new File(Deploy.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    StickyBootstrap b = new StickyBootstrap(zip);
    b.boot();
    b.load("net.stickcode.deploy.tomcat.DeploymentConfiguration");
  }

}
