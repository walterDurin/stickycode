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

import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StickyGuiceContextListenerTest {

  @Mock
  private ServletContext context;

  @InjectMocks
  StickyGuiceContextListener l = new StickyGuiceContextListener();

  @Mock
  private ServletContextEvent event;

  @Test
  public void notSet() {
    l.initialisePackagesToScan(context);
    l.getInjector();
  }

  @Test
  public void singlePackage() {
    when(context.getInitParameter("sticky-application-packages")).thenReturn("net.stickycode.other");
    l.initialisePackagesToScan(context);
    l.getInjector();
  }

  @Test
  public void multiplePackage() {
    when(context.getInitParameter("sticky-application-packages")).thenReturn("net.stickycode.other,org.mockwire.other");
    l.initialisePackagesToScan(context);
    l.getInjector();
  }
  
  @Test
  public void init() {
    when(event.getServletContext()).thenReturn(context);
    when(context.getInitParameter("sticky-application-packages")).thenReturn("net.stickycode.other,org.mockwire.other");
    l.contextInitialized(event);
  }
}
