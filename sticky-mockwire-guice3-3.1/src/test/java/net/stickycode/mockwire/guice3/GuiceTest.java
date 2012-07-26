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
package net.stickycode.mockwire.guice3;

import javax.inject.Inject;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import static org.fest.assertions.Assertions.assertThat;



public class GuiceTest {


  public static interface SomeInterface {

  }

  public static class SomeObject implements SomeInterface {

  }

  @Inject
  private SomeObject o;

  @Inject
  private SomeInterface b;

  @Test
  public void test() {
    AbstractModule abstractModule = new AbstractModule() {
      @Override
      public void configure() {
        bind(SomeInterface.class).toInstance(new SomeObject());
      }
    };
    Injector i = Guice.createInjector(Stage.PRODUCTION, abstractModule);
    assertThat(i.getInstance(SomeInterface.class)).isNotNull();
    assertThat(i.getBindings().size()).isPositive();
    i.injectMembers(this);
    assertThat(o).isNotNull();
    assertThat(b).isNotNull();

  }

}
