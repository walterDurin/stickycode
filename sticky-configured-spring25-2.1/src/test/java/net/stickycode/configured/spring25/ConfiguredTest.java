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

import java.util.List;

import org.junit.Test;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;

public class ConfiguredTest {

  public class ConfiguredTestObject {

    @Configured
    String bob;

    @Configured
    List<Integer> numbers;
  }

  ConfiguredTestObject configured = new ConfiguredTestObject();

  @Test
  public void configured() {
    configure();
    assertThat(configured.bob)
        .describedAs("Configured field was not set")
        .isNotNull();

    assertThat(configured.numbers).containsExactly(1, 3, 5, 7);
  }

  private void configure() {
    ConfigurationSystem system = new ConfigurationSystem();
    system.add(new ConfigurationSource() {

      @Override
      public boolean hasValue(String key) {
        if (key.endsWith("bob"))
          return true;

        return "configuredTestObject.numbers".equals(key);
      }

      @Override
      public String getValue(String key) {
        if (key.endsWith("bob"))
          return "jones";

        return "1,3,5,7";
      }
    });

    ConfiguredBeanPostProcessor processor = new ConfiguredBeanPostProcessor();
    processor.setConfiguration(system);
    processor.postProcessAfterInstantiation(configured, "configured");
  }

}
