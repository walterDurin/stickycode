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
package net.stickycode.mockwire.configured;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import net.stickycode.mockwire.Mockwire;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.stereotype.configured.Configured;

import org.junit.Test;


public class ConfiguredCollectionTest {

  @MockwireConfigured("configuredObject.numbers=1,3,5,7,11")
  public static class StringConfigured {
    @UnderTest
    ConfiguredObject configured;
  }

  @MockwireConfigured
  public static class PropertiesConfigured {
    @UnderTest
    ConfiguredObject configured;
  }

  public static class ConfiguredObject {
    @Configured
    List<Integer> numbers;

    public ConfiguredObject() {
      super();
    }
  }

  @Test
  public void inlineConfigured() {
    StringConfigured testInstance = new StringConfigured();
    Mockwire.isolate(testInstance);
    assertThat(testInstance.configured.numbers).containsExactly(1,3,5,7,11);
  }

  @Test
  public void propertiesConfigured() {
    PropertiesConfigured testInstance = new PropertiesConfigured();
    Mockwire.isolate(testInstance);
    assertThat(testInstance.configured.numbers).containsExactly(11,7,5,3,1);
  }
}
