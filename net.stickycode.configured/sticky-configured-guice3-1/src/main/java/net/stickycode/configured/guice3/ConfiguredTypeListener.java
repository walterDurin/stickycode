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
package net.stickycode.configured.guice3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.ConfiguredStrategy;
import net.stickycode.stereotype.PostConfigured;
import net.stickycode.stereotype.PreConfigured;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

@StickyComponent
@StickyFramework
public class ConfiguredTypeListener
    implements TypeListener {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private ConfiguredInjector membersInjector;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (typeIsConfigured(type)) {
      if (membersInjector == null)
        throw new AssertionError("On hearing " + type + " found that " + getClass().getSimpleName() + " was not injected with a "
            + ConfiguredInjector.class.getSimpleName());

      encounter.register(membersInjector);
      log.debug("encountering {} registering injector {}", type, membersInjector);
    }
  }

  private <I> boolean typeIsConfigured(TypeLiteral<I> type) {
    for (Class<? super I> current = type.getRawType(); current != null; current = current.getSuperclass()) {
      for (Field field : current.getDeclaredFields()) {
        if (field.isAnnotationPresent(Configured.class))
          return true;
        
        if (field.isAnnotationPresent(ConfiguredStrategy.class))
          return true;
      }
      
      for (Method method : current.getDeclaredMethods()) {
        if (method.isAnnotationPresent(PostConfigured.class))
          return true;
        
        if (method.isAnnotationPresent(PreConfigured.class))
          return true;
      }
    }
    
    return false;
  }
}
