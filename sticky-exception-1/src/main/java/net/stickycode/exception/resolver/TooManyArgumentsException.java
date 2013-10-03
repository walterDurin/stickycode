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

import net.stickycode.exception.InvalidParameterException;


/**
 * You tried to do something like this
 * <pre>
 * new SomeException("Some {} message", "param 1", "param 2");
 * </pre>
 * Obviously param 2 will disappear which is very annoying, to this error warns you about that.
 */
@SuppressWarnings("serial")
class TooManyArgumentsException
    extends InvalidParameterException {

  public TooManyArgumentsException(Object[] parameters, int placeHolderCount, String message) {
    super(
        "Found {} parameters " +
    		"but only {} places for them in '{}'. " +
    		"Add some placeholders or remove some arguments. " +
    		"The parameters were {}",
        parameters.length, placeHolderCount, message,
        parameters);
  }

}
