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
package net.stickycode.guice3.jsr250;

import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.matcher.Matchers;

import net.stickycode.configured.InvokingAnnotatedMethodProcessor;
import net.stickycode.reflector.Reflector;

public class Jsr250Module
    extends AbstractModule {

  @Override
  protected void configure() {
    bindListener(Matchers.any(), new Jsr250TypeListener());
  }

  public static void preDestroy(Injector injector) {
    for (Entry<Key<?>, Binding<?>> binding : injector.getAllBindings().entrySet()) {
      new Reflector()
          .forEachMethod(new InvokingAnnotatedMethodProcessor(PreDestroy.class))
          .process(binding.getValue().getProvider().get());
    }
  }

}
