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

import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class MissingBeanException
    extends PermanentException {


  public MissingBeanException(Object target, String missingBeanName, Class<?> missingBeanType) {
    super("Missing a bean named {} of type {} when attempting to inject {}", missingBeanName, missingBeanType, target.getClass().getSimpleName());
  }

  public MissingBeanException(Object target, Class<?> missingBeanType) {
    super("Missing a bean of type {} when attempting to inject {}", missingBeanType.getSimpleName(), target.getClass().getSimpleName());
  }

  public MissingBeanException(Throwable t, Object target, String missingBeanName, Class<?> missingBeanType) {
    super(t, "Missing a bean named {} of type {} when attempting to inject {}", missingBeanName, missingBeanType, target.getClass().getSimpleName());
  }

  public MissingBeanException(Throwable t, Object target, Class<?> missingBeanType) {
    super(t, "Missing a bean of type {} when attempting to inject {}", missingBeanType.getSimpleName(), target.getClass().getSimpleName());
  }

  public MissingBeanException(Class<?> type) {
    super("Missing bean of type {}, expected 1 in the context but found none", type.getSimpleName());
  }

}
