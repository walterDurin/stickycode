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
package net.stickycode.mockwire.guice3;

import net.stickycode.mockwire.MockwireTck;
import net.stickycode.mockwire.direct.MockwireDirectTck;
import net.stickycode.mockwire.junit4.MockwireRunnerTck;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MockwireTck.class,
    MockwireDirectTck.class,
    MockwireRunnerTck.class })
public class MockwireTckTest {

  /**
   * This is an anchor for Infinitest to rerun this suite if its changes
   */
  GuiceIsolatedTestManifest anchor;

}
