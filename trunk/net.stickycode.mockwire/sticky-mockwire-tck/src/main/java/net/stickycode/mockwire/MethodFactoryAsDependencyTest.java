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

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.junit4.MockwireRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockwireRunner.class)
public class MethodFactoryAsDependencyTest {

  @Controlled
  Mockable mockable;

	@UnderTest
	AutowirableWithDependencies underTest;

	@Inject
	Autowirable nested;

	@Uncontrolled
  public Autowirable factory() {
    return new Autowirable();
  }

	@Inject
	IsolatedTestManifest context;

	@Test
	public void underTest() {
	  assertThat(context.hasRegisteredType(AutowirableWithDependencies.class)).isTrue();
	  assertThat(context.hasRegisteredType(Mockable.class)).isTrue();
	  assertThat(context.hasRegisteredType(Autowirable.class)).isTrue();
	  
	  assertThat(underTest).isNotNull();
	  assertThat(mockable).isNotNull();
	  assertThat(nested).isNotNull();
	}
}
