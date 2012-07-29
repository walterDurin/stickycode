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

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfigurationSystem;

import org.junit.Test;

public abstract class AbstractScheduledComponentTest {

  protected abstract void configure(ScheduleTestObject instance);

  @Inject
  ConfigurationRepository configurations;

  @Inject
  ConfigurationSystem system;
  
  @Test
  public void verifyScheduling() {
    ScheduleTestObject instance = new ScheduleTestObject();
    configure(instance);

    assertThat(system)
        .as("Implementors must inject(this) so that the configuration system can be configured")
        .isNotNull();

    system.start();

    assertThat(configurations).hasSize(2);
    Configuration c = configurations.iterator().next();
    assertThat(c).hasSize(1);
    
  }

}
