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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.stickycode.mockwire.feature.MockwireScan;

/**
 * Define configuration used for configuring wired components in the Mockwire context.
 *
 * <pre>
 *   package net.stickycode.example;
 *
 *    &#064;RunWith(MockwireRunner.class)
 *    &#064;MockwireContainment()
 *    &#064;MockwireConfigured("a.b=value")
 *    public class ContainedTest {
 *      public static Class A {
 *        &#064;Configured
 *        String b;
 *      }
 *
 *      &#064;UnderTest
 *      A a;
 *
 *      &#064;Test
 *      public void check() {
 *        assertThat(a.b).isEqualTo("value");
 *      }
 *
 *   }
 * </pre>
 *
 * Given a properties file <code>configured.properties</code> in the default package
 * <pre>
 *    a.b=frompropfile
 *  </pre>
 *
 *  And a test class with the default {@link MockwireConfigured}
 *  <pre>
 *   package net.stickycode.example;
 *
 *    &#064;RunWith(MockwireRunner.class)
 *    &#064;MockwireContainment
 *    &#064;MockwireConfigured
 *    public class ContainedTest {
 *      public static Class A {
 *        &#064;Configured
 *        String b;
 *      }
 *
 *      &#064;UnderTest
 *      A a;
 *
 *      &#064;Test
 *      public void check() {
 *        assertThat(a.b).isEqualTo("frompropfile");
 *      }
 *
 *   }
 * </pre>
 *
 * Given a properties file <code>other.properties</code> in the default package
 * <pre>
 *    a.b=fromotherpropfile
 *  </pre>
 *
 *  And a test class with the property file parameter default {@link MockwireConfigured}
 *  <pre>
 *   package net.stickycode.example;
 *
 *    &#064;RunWith(MockwireRunner.class)
 *    &#064;MockwireContainment
 *    &#064;MockwireConfigured("/other.properties")
 *    public class ContainedTest {
 *      public static Class A {
 *        &#064;Configured
 *        String b;
 *      }
 *
 *      &#064;UnderTest
 *      A a;
 *
 *      &#064;Test
 *      public void check() {
 *        assertThat(a.b).isEqualTo("fromotherpropfile");
 *      }
 *
 *   }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@MockwireScan({
  "net.stickycode.bootstrap", 
  "net.stickycode.configured", 
  "net.stickycode.configuration", 
  "net.stickycode.metadata", 
  "net.stickycode.coercion",
  "net.stickycode.resource",
  "net.stickycode.xml.jaxb"
  })
public @interface MockwireConfigured {

  /**
   * The properties files or values used for configuring wired components.
   *
   */
  String[] value() default { "configured.properties" };

}
