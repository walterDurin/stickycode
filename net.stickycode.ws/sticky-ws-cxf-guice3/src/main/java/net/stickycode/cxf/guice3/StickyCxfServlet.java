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
package net.stickycode.cxf.guice3;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.cxf.Bus;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@Singleton
public class StickyCxfServlet
    extends CXFNonSpringServlet {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private Bus bus;
  
  @Override
  protected void loadBus(ServletConfig sc) {
    log.info("load bus {}", bus);
    setBus(bus);
  }

  @Override
  public void init(ServletConfig sc) throws ServletException {
    super.init(sc);
    log.info("initialised {}", this);
  }
  
}
