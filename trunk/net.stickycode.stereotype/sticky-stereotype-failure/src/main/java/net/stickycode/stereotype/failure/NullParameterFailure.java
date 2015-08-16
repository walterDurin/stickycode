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
package net.stickycode.stereotype.failure;

import static net.stickycode.stereotype.failure.FailureClassification.InvalidRequest;

/**
 * Thrown to indicate a null parameter was passed which should always be considered a bad thing.
 */
@SuppressWarnings("serial")
@Failure(InvalidRequest)
public class NullParameterFailure
  extends ParameterisedFailure {

  public NullParameterFailure(String message, Object... arguments) {
    super(message, arguments);
  }


}
