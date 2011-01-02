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
package net.stickycode.configured.spring25;

import org.junit.Test;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;

public class ConfiguredTest {

  public class ConfiguredTestObject {

    @Configured
    String bob;
  }

  ConfiguredTestObject configured;

  @Test
  public void configured() {
    configure();
    assertThat(configured.bob).describedAs("Configured field was not set").isNotNull();
  }

  private void configure() {
    configured = new ConfiguredTestObject();

    ConfigurationSystem system = new ConfigurationSystem();
    system.add(new ConfigurationSource() {

      @Override
      public boolean hasValue(String key) {
        return "configuredTestObject.bob".equals(key);
      }

      @Override
      public String getValue(String key) {
        return "jones";
      }
    });

    ConfiguredBeanPostProcessor processor = new ConfiguredBeanPostProcessor();
    processor.configuration = system;
    processor.postProcessAfterInstantiation(configured, "configured");
    system.configure();
  }

}
