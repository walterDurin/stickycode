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
package net.stickycode.scheduled.guice3;

import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.stereotype.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class ScheduledTypeListener
    implements TypeListener {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private ScheduledInjector membersInjector;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (typeIsScheduled(type)) {
      if (membersInjector == null)
        throw new AssertionError(getClass().getSimpleName() + " was not injected with a " + ScheduledInjector.class.getSimpleName());

      encounter.register(membersInjector);
      log.info("encountering {} registering injector {}", type, membersInjector);
    }
  }

  private <I> boolean typeIsScheduled(TypeLiteral<I> type) {
    for (Method method : type.getRawType().getDeclaredMethods())
      if (method.isAnnotationPresent(Scheduled.class))
        return true;

    return false;
  }
}
