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
 * See {@link UnderTest} as its a more descriptive name
 *
 * Mark a field as defining a bean in an isolated test context and for injection of the value created in the isolated test context.
 *
 * In the following example <code>ConcreteClass</code> will be blessed into a singleton in the isolated test context created
 * by <code>Mockwire.isolate()</code> or using <code>&#064;RunWith(MockwireRunner.class)</code>
 *
 * <pre>
 *  public class MockwireTest {
 *
 *  &#064;Bless
 *  ConcreteClass field;
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
 *    assertThat(context.getBeanNamesForType(ConcreteClass.class)).hasSize(1);
 *    assertThat(field).isNotNull();
 *  }
 *
 * </pre>
 *
 * <pre>
 *  &#064;RunWith(MockwireRunner.class)
 *  public class MockwireTest {
 *
 *  &#064;Bless
 *  ConcreteClass field;
 *
 *  &#064;Inject
 *  IsolateTestContext context;
 *
 *  &#064;Test
 *  public void testBless() {
 *    assertThat(context.getBeanNamesForType(ConcreteClass.class)).hasSize(1);
 *    assertThat(field).isNotNull();
 *  }
 *
 * </pre>
 *
 * @See {@link UnderTest}
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Deprecated
public @interface Bless {

}
