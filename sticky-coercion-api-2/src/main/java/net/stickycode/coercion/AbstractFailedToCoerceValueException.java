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
package net.stickycode.coercion;

import net.stickycode.exception.PermanentException;

/**
 * A super type for exceptions that can be thrown during coercion.
 *
 * This exception is abstract as each coercion should have specific well named exceptions of its own.
 *
 * All coercion failures are permanent as coercions should not rely on any other systems so all failures will recur.
 */
@SuppressWarnings("serial")
public abstract class AbstractFailedToCoerceValueException
    extends PermanentException {

  /**
   * Construct a coercion failure message where there was an exceptional cause.
   *
   * @param cause The cause of the problem
   * @param message The parameterized message
   * @param arguments The arguments for parameterization, can be empty
   */
  public AbstractFailedToCoerceValueException(Throwable cause, String message, Object... arguments) {
    super(cause, message, arguments);
  }

  /**
   * Construct a coercion failure message.
   *
   * @param message The parameterized message
   * @param arguments The arguments for parameterization, can be empty
   */
  public AbstractFailedToCoerceValueException(String message, Object... arguments) {
    super(message, arguments);
  }

}
