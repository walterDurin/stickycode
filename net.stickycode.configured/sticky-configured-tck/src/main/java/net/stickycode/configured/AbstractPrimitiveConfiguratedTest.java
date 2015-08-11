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

import javax.inject.Inject;

import net.stickycode.stereotype.configured.Configured;

import org.junit.Test;

public abstract class AbstractPrimitiveConfiguratedTest {

  public class NotConfiguredTestObject {

    @Configured
    Boolean noDefault;
  }

  public class PrimitiveTestObject {

    @Configured
    boolean primitivesNotConfigurable;
  }

  @Inject
  private ConfigurationSystem system;

  protected abstract void configure(Object target);//, ConfigurationResolutions configurationSource);

  @Test(expected = ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossibleException.class)
  public void primitivesHaveNoDefaults() {
    configure(new PrimitiveTestObject());//, mock(ConfigurationResolutions.class));
    assertThat(system)
        .as("Implementors must inject/wire(this) so that the configuration system is available for configuring")
        .isNotNull();
  }

  @Test(expected = MissingConfigurationException.class)
  public void notConfiguredThrowsANiceException() {
    configure(new NotConfiguredTestObject());//, mock(ConfigurationResolutions.class));
    assertThat(system)
        .as("Implementors must inject/wire(this) so that the configuration system is available for configuring")
        .isNotNull();
    system.start();
  }

}
