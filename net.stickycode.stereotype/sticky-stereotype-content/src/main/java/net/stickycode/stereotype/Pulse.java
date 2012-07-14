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
package net.stickycode.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Deprecated as the Resource stereotype is a separate project to separate concerns.
 * 
 * <pre>
 * &lt;dependency&gt;
 * &lt;groupId&gt;net.stickycode.heartbeat&lt;/groupId&gt;
 * &lt;artifactId&gt;sticky-heartbeat-stereotype&lt;/artifactId&gt;
 * &lt;version&gt;[1,2)&lt;/version&gt;
 * &lt;/dependency&gt;
 * </pre>
 */
@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pulse {

  /**
   * Describe the pulse such that when this message is presented to the user they can configure the schedule
   * appropriately
   */
  String value() default "";
}
