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


public interface IsolatedTestManifest extends ParameterSource {

  /**
   * Return true if a <code>type</code> is registered in this manifest,
   * essentially that includes any bean that could be assigned to this type.
   */
  boolean hasRegisteredType(Class<?> type);

  /**
   * Register <code>bean</code> as a fully fledged object with name <code>beanName</code> ins the manifest.
   *
   * The type is passed as well in case deriving the type correctly
   *
   * @param beanName The name of the provided bean
   * @param bean The bean to add to the manifest
   * @param type The type of the bean being added, this is useful when the beans type is not clear think cglib.
   */
  void registerBean(String beanName, Object bean, Class<?> type);

  /**
   * Register factory in the manifest to create a bean named <code>beanName</code> of <code>type</code>
   * @param beanName The name of the bean that will be created
   * @param type The type of the bean to be created
   */
  void registerType(String beanName, Class<?> type);

  /**
   * Wire up the given testInstance by type using this manifest.
   */
  void autowire(Object testInstance) throws MissingBeanException;

  /**
   * Scan for components in from the given package roots
   */
  void scanPackages(String[] scanRoots);

}
