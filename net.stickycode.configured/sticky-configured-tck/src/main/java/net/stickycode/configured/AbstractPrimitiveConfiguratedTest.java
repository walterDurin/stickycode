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

import org.junit.Test;

import net.stickycode.stereotype.Configured;

import static org.mockito.Mockito.mock;

public abstract class AbstractPrimitiveConfiguratedTest {

  public class NotConfiguredTestObject {

    @Configured
    Boolean noDefault;
  }

  public class PrimitiveTestObject {

    @Configured
    boolean primitivesNotConfigurable;
  }

  protected abstract void configure(Object target, ConfigurationSource configurationSource);

  @Test(expected = ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossibleException.class)
  public void primitivesHaveNoDefaults() {
    configure(new PrimitiveTestObject(), mock(ConfigurationSource.class));
  }

  @Test(expected = ConfigurationValueNotFoundForKeyException.class)
  public void notConfiguredThrowsANiceException() {
    configure(new NotConfiguredTestObject(), mock(ConfigurationSource.class));
  }

}
