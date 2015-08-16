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
package net.stickycode.stereotype.failure.resolver;

import java.security.InvalidParameterException;

public class ParameterResolver {

  ParameterResolver() {
  }

  /**
   * Resolve all the placeholders <code>{}</code> in the message to the given arguments
   *
   * <pre>
   * resolve("Hi {}", "bob").equals("Hi bob");
   * resolve("Hi {} and {}, "bob", "alice").equals("Hi bob and alice");
   * </pre>
   *
   * @param message The message with placeholders
   * @param arguments The arguments to put in the placeholders
   * @throws InvalidParameterException If there is not a placeholder for every argument
   * @throws InvalidParameterException If there is not an argument for every placeholder
   * @throws InvalidParameterException If
   * @return The resolved string
   */
  public static String resolve(String message, Object... arguments)
      throws InvalidParameterException {

    if (message == null)
      throw new NullPointerException("Failure message should not be null");

    if (arguments == null)
      throw new NullPointerException(
          "Failure arguments should not be null, you can just leave the arguments off if there aren't any, but never pass null.");

    if (message.trim().length() == 0)
      throw new IllegalArgumentException("Failure message should not be blank");

    return resolveMessage(message, arguments);
  }

  private static String resolveMessage(String message, Object[] parameters) {
    Fragments fragments = new Fragments(message);
    int i = 0;
    StringBuilder b = new StringBuilder();
    for (Fragment fragment : fragments) {
      if (fragment.isText()) {
        b.append(fragment.getValue());
      }
      else {
        if (i >= parameters.length)
          throw new TooManyPlaceHoldersException(parameters.length, i, message);

        if (fragment.isParameter()) {
          b.append(map(filterThrowableParameters(parameters[i])));
          i++;
        }
        if (fragment.isQuote()) {
          b.append("'").append(map(filterThrowableParameters(parameters[i]))).append("'");
          i++;
        }
      }
    }

    if (i < parameters.length)
      throw new TooManyArgumentsException(
          parameters, i, message);

    return b.toString();
  }

  @SuppressWarnings("rawtypes")
  private static Object map(Object object) {
    if (object.getClass() == Class.class)
      return ((Class) object).getSimpleName();

    return object;
  }

  private static Object filterThrowableParameters(Object object) {
    if (object == null)
      throw new NullPointerException("Passing null parameters to failure constructors is not good practice");

    if (object instanceof Throwable)
      throw new ThrowablesCannotBeParametersException((Throwable) object);

    return object;
  }

}
