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

import net.stickycode.configured.AbstractPrimitiveConfiguratedTest;
import net.stickycode.configured.ConfigurationSource;
import net.stickycode.configured.ConfigurationSystem;

public class PrimitiveHaveNoDefaultsTest
    extends AbstractPrimitiveConfiguratedTest {

  @Override
  protected void configure(Object target, ConfigurationSource configurationSource) {
    ConfigurationSystem system = new ConfigurationSystem(configurationSource);
    ConfiguredBeanPostProcessor processor = new ConfiguredBeanPostProcessor();
    processor.setConfiguration(system);
    processor.postProcessAfterInstantiation(target, "configured");
    system.configure();
  }
}
