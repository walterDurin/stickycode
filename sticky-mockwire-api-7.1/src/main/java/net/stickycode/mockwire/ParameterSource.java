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
package net.stickycode.mockwire;

/**
 * A source of instantiated beans that can be looked up by type.
 */
public interface ParameterSource {

  /**
   * Return a instance of the given bean type.
   * 
   * @throws MissingBeanException if there is no instance bound to type
   * @throws NonUniqueBeanException if more than on instance of the given type exists in the context
   * 
   * @return An instance of the given type
   */
  <T> T getBeanOfType(Class<T> type)
      throws MissingBeanException, NonUniqueBeanException;

}
