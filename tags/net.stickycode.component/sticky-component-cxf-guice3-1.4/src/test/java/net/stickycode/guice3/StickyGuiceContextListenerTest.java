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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StickyGuiceContextListenerTest {

  @Mock
  private ServletContext context;

  @Test
  public void notSet() {
    StickyGuiceContextListener l = new StickyGuiceContextListener();
    l.createInjector(context);
  }

  @Test
  public void singlePackage() {
    StickyGuiceContextListener l = new StickyGuiceContextListener();
    when(context.getInitParameter("sticky-application-packages")).thenReturn("net.stickycode.other");
    l.createInjector(context);
  }

  @Test
  public void multiplePackage() {
    StickyGuiceContextListener l = new StickyGuiceContextListener();
    when(context.getInitParameter("sticky-application-packages")).thenReturn("net.stickycode.other,org.mockwire.other");
    l.createInjector(context);
  }
}
