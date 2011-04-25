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

import java.lang.reflect.Field;

import org.junit.Test;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.Configured;

import static org.mockito.Mockito.mock;

import static org.fest.assertions.Assertions.assertThat;

public class ConfiguredTest {

  @Configured
  String configured;

  String notConfigured;

  @Test
  public void notConfigured() {
    ConfiguredFieldProcessor configuredFieldProcessor = new ConfiguredFieldProcessor(null);
    assertThat(configuredFieldProcessor.canProcess(getField("notConfigured"))).isFalse();
  }

  @Test
  public void configured() {
    ConfiguredFieldProcessor configuredFieldProcessor = new ConfiguredFieldProcessor(null);
    assertThat(configuredFieldProcessor.canProcess(getField("configured"))).isTrue();
  }

  @Test
  public void configured2() {
    ConfigurationSystem mock = mock(ConfigurationSystem.class);
    ConfiguredFieldProcessor configuredFieldProcessor = new ConfiguredFieldProcessor(mock);
    assertThat(configuredFieldProcessor.canProcess(getField("configured"))).isTrue();
  }

  private Field getField(String fieldName) {
    try {
      return getClass().getDeclaredField(fieldName);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }


}
