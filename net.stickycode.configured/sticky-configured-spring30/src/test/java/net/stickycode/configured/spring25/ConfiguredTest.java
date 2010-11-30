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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockwireRunner.class)
@MockwireContainment("/net.stickycode.configured")
public class ConfiguredTest {

  static public class ConfiguredTestObject {
    @Configured
    String bob;
  }

  @BeforeClass
  static public void before() {
    System.setProperty("configuredTestObject.bob", "jones");
  }

  @UnderTest
  ConfiguredTestObject configured;

  @Test
  public void configured() {
    assertThat(configured).describedAs("Object under test was not injected").isNotNull();
    assertThat(configured.bob).describedAs("Configured field was not set").isNotNull();
  }

}
