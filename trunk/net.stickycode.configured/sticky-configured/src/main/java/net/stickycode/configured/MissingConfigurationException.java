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
package net.stickycode.configured;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationResolver;
import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class MissingConfigurationException
    extends PermanentException {

  public MissingConfigurationException(ConfigurationKey key, ConfigurationResolver sources) {
    super("A value for '{}' could not be found in '{}'", key, sources);
  }

}
