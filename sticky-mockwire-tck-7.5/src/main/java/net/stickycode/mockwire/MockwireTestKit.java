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
package net.stickycode.mockwire;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.stickycode.mockwire.configured.MockwireConfiguredTck;
import net.stickycode.mockwire.contained.MockwireContainmentTck;
import net.stickycode.mockwire.direct.MockwireDirectTck;
import net.stickycode.mockwire.junit4.MockwireRunnerTck;

@RunWith(Suite.class)
@SuiteClasses({
  MockwireTck.class,
  MockwireConfiguredTck.class,
  MockwireContainmentTck.class,
  MockwireDirectTck.class,
  MockwireRunnerTck.class
})
public class MockwireTestKit {

}
