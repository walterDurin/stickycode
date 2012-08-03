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

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.configuration.ConfigurationTarget;
import net.stickycode.configuration.ResolvedConfiguration;

/**
 * A configured attribute of a bean.
 * 
 * An attribute can have a default value which is the coding time value of the attribute being the value the developer
 * thought was the most appropriate value when writing the code.
 * 
 */
public interface ConfigurationAttribute
    extends ConfigurationTarget {

  void applyCoercion(CoercionFinder coercions);

  void update();

  void invertControl(ComponentContainer container);
  
  boolean requiresResolution();
  
  ResolvedConfiguration getResolution();

  Object getTarget();

}
