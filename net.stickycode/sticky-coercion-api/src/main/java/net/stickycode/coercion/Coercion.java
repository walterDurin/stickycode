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
package net.stickycode.coercion;

/**
 * Contract for a converter of strings to a defined type.
 * 
 * @param <T> The type that can be converted to
 */
public interface Coercion<T> {

  /**
   * Coerce the given string value into the type represented by this coercion.
   * 
   * @param value The string value to convert. WILL NOT be null.
   * @return A non null string value
   */
  T coerce(CoercionTarget type, String value);

  /**
   * Return true if this coercion is applicable for the given target type
   */
  boolean isApplicableTo(CoercionTarget target);

  /**
   * For some coercions a default value on the coercion itself makes sense.
   */
  boolean hasDefaultValue();

  /**
   * In the case where there is not value already set and a String value for coercion cannot be found then this will be used.
   */
  T getDefaultValue(CoercionTarget target);

  /**
   * Description of the coercion useful for logging
   */
  String toString();
}
