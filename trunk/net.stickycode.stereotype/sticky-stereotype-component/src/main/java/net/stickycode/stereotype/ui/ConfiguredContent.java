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
package net.stickycode.stereotype.ui;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>The content of a system is often expected to change at runtime and is not defined by the development team, this annotation is used
 * to mark a fields as being injected from an external system.</p>
 *
 * <p>Content fields must be of type String anything else will error</p>
 *
 * <p>If the field is a string then any localization will be carried out by the configuration system</p>
 *
 * <h3>TODO</h3>
 * <p>Allow fields of type LocalizedContent</p>
 * <p>For LocalizedContent content will be localized by the user associated with the request</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfiguredContent {
  /**
   * Describe the configuration such that someone reading this message could provide appropriate configuration
   */
  String value() default "";
}
