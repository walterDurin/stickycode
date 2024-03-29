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

import javax.annotation.PostConstruct;

import com.google.inject.spi.InjectionListener;

import net.stickycode.configured.InvokingAnnotatedMethodProcessor;
import net.stickycode.reflector.Reflector;

public class PostConstructInjectionListener
    implements InjectionListener<Object> {

  @Override
  public void afterInjection(Object injectee) {
    new Reflector()
        .forEachMethod(new InvokingAnnotatedMethodProcessor(PostConstruct.class))
        .process(injectee);
  }

}
