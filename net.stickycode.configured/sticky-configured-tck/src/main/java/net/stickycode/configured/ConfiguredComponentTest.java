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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.stereotype.configured.Configured;

import org.junit.Test;

public class ConfiguredComponentTest {

  public class ConfiguredTestObject {

    @Configured
    String bob;

    @Configured
    List<Integer> numbers;
  }

  public class InheritedConfiguredTestObject extends ConfiguredTestObject {

  }

  @Inject
  ConfigurationRepository configurations;

  @Inject
  ConfigurationSystem system;

  @Test
  public void verifySystemConfigured() {
    ConfiguredTestObject instance = new ConfiguredTestObject();
    verify(instance);
  }

  @Test
  public void configuredInheritance() {
    ConfiguredTestObject instance = new InheritedConfiguredTestObject();
    verify(instance);
  }

  private void verify(ConfiguredTestObject instance) {
    StickyBootstrap.crank(this, getClass()).inject(instance);

    assertThat(system)
        .as("Implementors must inject(this) so that the configuration system can be configured")
        .isNotNull();

    system.start();

    assertThat(instance.bob)
        .as("Injector should have configured a value")
        .isNotNull();
    assertThat(instance.bob).isEqualTo("yay");

    assertThat(instance.numbers)
        .as("Injector should have configured a value")
        .isNotNull();
    assertThat(instance.numbers).containsExactly(1, 5, 3, 7);

    assertThat(configurations).hasSize(1);
    Configuration c = configurations.iterator().next();
    assertThat(c).hasSize(3);
  }

}
