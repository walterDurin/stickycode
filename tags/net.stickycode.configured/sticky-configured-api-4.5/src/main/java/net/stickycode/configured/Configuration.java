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
 * The configuration for a bean in an application.
 */
public interface Configuration
    extends Iterable<ConfigurationAttribute> {

  /**
   * Invoke any preconfiguration hooks on the bean.
   */
  void preConfigure();

  /**
   * Invoke any postconfiguration hooks on the bean.
   */
  void postConfigure();

  void register(ConfigurationAttribute attribute);

}
