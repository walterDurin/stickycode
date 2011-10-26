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
package net.stickycode.configured;

import net.stickycode.coercion.CoercionTarget;

/**
 * A configured attribute of a bean.
 *
 * An attribute can have a default value which is the coding time value of the attribute being the value the developer
 * thought was the most appropriate value when writing the code.
 *
 */
public interface ConfigurationAttribute
    extends CoercionTarget, ConfigurationKeyElement {

  /**
   * Return true if there was a default value defined at development time.
   * @return true if there is a default value for the attribute
   */
  boolean hasDefaultValue();

  /**
   * The default value of the attribute as defined at development time.
   * @return The default value of the attribute as defined at development time.
   */
  Object getDefaultValue();

  /**
   * Set the underlying attribute.
   * @param value The new value of the attribute
   */
  void setValue(Object value);

  /**
   * Return the name of the underlying attribute
   * @return The name of the attribute
   */
  String getName();

}
