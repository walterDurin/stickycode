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

import net.stickycode.coercion.Coercion;
import net.stickycode.configured.guice3.ConfiguredModule;
import net.stickycode.scheduled.InMemoryScheduledRunnableRepository;
import net.stickycode.scheduled.ScheduledRunnableRepository;
import net.stickycode.scheduled.SchedulingSystem;
import net.stickycode.scheduled.configuration.ScheduleConfigurationCoercion;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

public class ScheduledModule
    extends AbstractModule {

  @Override
  protected void configure() {
    bind(ScheduledInjector.class).in(Singleton.class);
    requireBinding(SchedulingSystem.class);
    bind(ScheduledRunnableRepository.class).to(InMemoryScheduledRunnableRepository.class).in(Singleton.class);
    ScheduledTypeListener listener = new ScheduledTypeListener();
    requestInjection(listener);
    bindListener(Matchers.any(), listener);
  }

}
