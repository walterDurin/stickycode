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
 * <p>
 * These exceptions deal with situations that are not program errors but relate
 * to the programs interaction.
 * </p>
 *
 * <p>Ideally they would be checked however if you follow
 * Clean Code pg 107 checked exceptions violate the Open/Closed Principle in that any time
 * you add a checked exception the ripple of code changes totally breaks encapsulation.
 * </p>
 * <p>
 * Best to just write good tests and document transient exceptions.
 * </p>
 *
 * @see Clean Code : Robert C Martin
 * @see http://www.javapractices.com/topic/TopicAction.do?Id=129
 */
@SuppressWarnings("serial")
public class TransientException
    extends RuntimeException {

  public TransientException(String message, Object... arguments) {
    super(ParameterResolver.resolve(message, arguments));
  }

  public TransientException(Throwable t, String message, Object... arguments) {
    super(ParameterResolver.resolve(message, arguments), t);
  }

  public boolean isRetryable() {
    return false;
  }
}
