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

import javax.inject.Inject;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

@StickyComponent
@StickyFramework
public class ScheduledTypeListener
    implements TypeListener {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private ScheduledInjector membersInjector;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (membersInjector == null) {
      log.debug("ignoring {} for scheduling as members injector not injected yet, perhaps you need a child injector", type);
      return;
    }

    if (membersInjector.isApplicable(type.getRawType())) {
      encounter.register(membersInjector);
      log.info("encountering {} registering injector {}", type, membersInjector);
    }
  }
}
