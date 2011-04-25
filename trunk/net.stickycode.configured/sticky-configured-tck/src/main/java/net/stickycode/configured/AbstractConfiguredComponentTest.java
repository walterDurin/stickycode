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
package net.stickycode.configured;

import java.util.List;

import org.junit.Test;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;

public abstract class AbstractConfiguredComponentTest {

  public class ConfiguredTestObject {

    @Configured
    String bob;

    @Configured
    List<Integer> numbers;
  }

  public AbstractConfiguredComponentTest() {
    super();
  }

  protected abstract ConfigurationSystem configure(ConfiguredTestObject instance);

  @Test
  public void verifySystemConfigured() {
    ConfiguredTestObject instance = new ConfiguredTestObject();
    ConfigurationSystem configuration = configure(instance);

    assertThat(instance.bob)
      .as("Injector should have configured a value")
      .isNotNull();
    assertThat(instance.bob).isEqualTo("yay");

    assertThat(instance.numbers)
      .as("Injector should have configured a value")
      .isNotNull();
    assertThat(instance.numbers).containsExactly(1, 5, 3, 7);

    assertThat(configuration.registeredFieldCount()).isEqualTo(2);
  }

}
