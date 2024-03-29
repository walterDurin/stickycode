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
package net.stickycode.configured.guice3;

import java.util.Collections;

import net.stickycode.configured.AbstractPrimitiveConfiguratedTest;
import net.stickycode.configured.ConfigurationSource;
import net.stickycode.exception.PermanentException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

public class PrimitiveHaveNoDefaultsTest
    extends AbstractPrimitiveConfiguratedTest {

  protected void configure(Object target, ConfigurationSource noConfigurationSource) {
    Injector injector = Guice.createInjector(new ConfigurationSourceModule(Collections.singletonList(noConfigurationSource)), new ConfiguredModule());
    try {
      injector.injectMembers(target);
      injector.injectMembers(this);
    }
    catch (ProvisionException e) {
      if (e.getCause() instanceof PermanentException)
        throw (PermanentException)e.getCause();

      throw e;
    }
  }

}
