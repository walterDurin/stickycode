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

import net.stickycode.configured.ConfigurationValueNotFoundForKeyException;
import net.stickycode.mockwire.Mockwire;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.stereotype.Configured;

public class PrimitiveHaveNoDefaultsTest {


  @MockwireContainment("/net.stickycode.configured")
  public class NotConfiguredTestObject {
    @Configured
    Boolean noDefault;
  }

  @MockwireContainment("/net.stickycode.configured")
  public class PrimitiveTestObject {
    @Configured
    boolean primitivesNotConfigurable;
  }

  @Test(expected=ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossible.class)
  public void primitivesHaveNoDefaults() {
    Mockwire.contain(new PrimitiveTestObject());
  }

  @Test(expected=ConfigurationValueNotFoundForKeyException.class)
  public void notConfiguredThrowsANiceException() {
    Mockwire.contain(new NotConfiguredTestObject());
  }
}
