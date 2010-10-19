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
package net.stickycode.exception.resolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.stickycode.exception.BlankParameterException;
import net.stickycode.exception.InvalidParameterException;
import net.stickycode.exception.NullParameterException;

public class ParameterResolver {

  ParameterResolver() {
  }

  /**
   * Resolve all the placeholders <code>{}</code> in the message to the given arguments
   * <pre>
   * resolve("Hi {}", "bob").equals("Hi bob");
   * resolve("Hi {} and {}, "bob", "alice").equals("Hi bob and alice");
   * </pre>
   *
   * <pre>
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
      throw new NullParameterException("Message should not be null");

    if (arguments == null)
      throw new NullParameterException("Arguments should not be null, you can just leave the arguments off if there aren't any, but never pass null.");

    if (message.trim().length() == 0)
      throw new BlankParameterException("Message should not be blank");

    return resolveMessage(message, arguments);
  }

  private static String resolveMessage(String message, Object[] parameters) {
    List<String> fragments = split(message);
    if (fragments.size() > parameters.length + 1)
      throw new TooManyPlaceHoldersException(
          parameters.length, fragments.size(), message);

    if (fragments.size() < parameters.length + 1)
      throw new TooManyArgumentsException(
          parameters, fragments.size(), message);

    return buildMessage(parameters, fragments);
  }

  static List<String> split(String message) {
    List<String> fragments = new ArrayList<String>();
    int lastIndex = 0; // because it supposed to be the beginning of a {}
    int index = message.indexOf("{}");
    if (index == -1)
      return Collections.singletonList(message);

    while (index >= 0) {
      fragments.add(message.substring(lastIndex, index));
      lastIndex = index + 2;
      index = message.indexOf("{}", lastIndex);
    }

    fragments.add(message.substring(lastIndex));

    return fragments;
  }

  private static String buildMessage(Object[] parameters, List<String> fragments) {
    StringBuilder b = new StringBuilder(fragments.get(0));
    for (int i = 0; i < parameters.length; i++) {
      filterThrowableParameters(parameters[i]);
      b.append(parameters[i]).append(fragments.get(i + 1));
    }
    return b.toString();
  }

  private static void filterThrowableParameters(Object object) {
    if (object == null)
      throw new NullParameterException("Passing null parameters to exception constructors is not good practice");

    if (object instanceof Throwable)
      throw new ThrowablesCannotBeParametersException((Throwable) object);
  }

}
