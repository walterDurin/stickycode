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
package net.stickycode.exception;

import net.stickycode.exception.resolver.ParameterResolver;

/**
 * An exception that is thrown when a permanent development intervention is most likely to be required.
 *
 * For example when null parameters or invalid parameters are passed around, unit and greater testing should
 * catch all of these errors.
 */
@SuppressWarnings("serial")
public class PermanentException
    extends RuntimeException {

  public PermanentException(String message, Object... arguments) {
    super(ParameterResolver.resolve(message, arguments));
  }

  public PermanentException(Throwable t, String message, Object... arguments) {
    super(ParameterResolver.resolve(message, arguments), t);
  }
}
