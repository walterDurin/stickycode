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

/**
 * A source of configuration values.
 */
public interface ConfigurationSource {

  /**
   * Return true if this configuration source has a value for the given key.
   *
   * @param key The key to configuration
   * @return true if this configuration source has a value for the given key
   */
  boolean hasValue(String key);

  /**
   * Return the value for configuration for the given key.
   *
   * As the {@link #hasValue(String)} should be called first implementations should never return null.
   *
   * @param key The key too lookup
   * @return The value found for the given key never null.
   */
  String getValue(String key)
    throws ConfigurationNotFoundException;

}
