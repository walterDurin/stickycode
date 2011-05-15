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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Empirically speaking this marks an object as being uncontrolled in the experiment, its not directly under test but can't be
 * mocked.
 *
 * Technically mark a field as defining a real bean in an isolated test context. The mockwire implementation MUST NOT inject this field to
 * avoid it actually being used in the test.
 *
 * In the following example <code>ConcreteClass</code> will be registered into a singleton in the isolated test context created
 * by <code>Mockwire.isolate()</code>.
 *
 * <pre>
 *  public class MockwireTest {
 *
 *  &#064;Uncontrolled
 *  ConcreteClass uncontrolled;
 *
 *  &#064;UnderTest
 *  OtherConcreteClass underTest;
 *
 *  &#064;Inject
 *  IsolateTestContext context;
 *
 *  &#064;Before
 *  public void setup() {
 *  	Mockwire.isolate(this);
 *  }
 *
 *  &#064;Test
 *  public void testBless() {
 *    assertThat(context.hasBeanOfType(ConcreteClass.class)).isTrue();
 *    assertThat(uncontrolled).isNull();
 *    assertThat(underTest).isNotNull();
 *  }
 *
 * </pre>
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Uncontrolled {

}
