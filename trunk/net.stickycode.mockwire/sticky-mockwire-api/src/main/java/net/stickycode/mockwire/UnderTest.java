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
package net.stickycode.mockwire;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * In empirical terms this is the object of investigation that we are going to prod and see what happens.
 * 
 * <p>
 * Technically we mark a field as defining a bean in an isolated test context and also mark the field for injection of the value
 * created in the isolated test context.
 * </p>
 * 
 * <p>
 * In the following example <code>ConcreteClass</code> will be blessed into a singleton in the isolated test context.
 * </p>
 * 
 * <h3>Using &#064;RunWith(MockwireRunner.class)</h3>
 * 
 * <pre>
 *  &#064;RunWith(MockwireRunner.class)
 *  public class MockwireTest {
 * 
 *  &#064;UnderTest
 *  ConcreteClass field;
 * 
 *  &#064;Test
 *  public void testUnderTest() {
 *    assertThat(field).isNotNull();
 *  }
 * 
 * </pre>
 * 
 * <h3>Using Mockwire.isolate()</h3>
 * 
 * <pre>
 *  public class MockwireTest {
 * 
 *  &#064;UnderTest
 *  ConcreteClass field;
 * 
 *  &#064;Before
 *  public void setup() {
 *    Mockwire.isolate(this);
 *  }
 * 
 *  &#064;Test
 *  public void testUnderTest() {
 *    assertThat(field).isNotNull();
 *  }
 * 
 * </pre>
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface UnderTest {

  /**
   * Configuration for the object under test, used to resolve any {@link Configured} fields
   */
  String[] value() default {};
}
