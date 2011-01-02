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

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.ConfigurationValueNotFoundForKeyException;
import net.stickycode.configured.ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossibleException;
import net.stickycode.stereotype.Configured;

public class PrimitiveHaveNoDefaultsTest {

  public class NotConfiguredTestObject {
    @Configured
    Boolean noDefault;
  }

  public class PrimitiveTestObject {
    @Configured
    boolean primitivesNotConfigurable;
  }

  @Test(expected=ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossibleException.class)
  public void primitivesHaveNoDefaults() {
    configure(new PrimitiveTestObject());
  }

  @Test(expected=ConfigurationValueNotFoundForKeyException.class)
  public void notConfiguredThrowsANiceException() {
    configure(new NotConfiguredTestObject());
  }

  private void configure(Object target) {
    ConfigurationSystem system = new ConfigurationSystem();
    ConfiguredBeanPostProcessor processor = new ConfiguredBeanPostProcessor();
    processor.configuration = system;
    processor.postProcessAfterInstantiation(target, "configured");
    system.configure();
  }
}
