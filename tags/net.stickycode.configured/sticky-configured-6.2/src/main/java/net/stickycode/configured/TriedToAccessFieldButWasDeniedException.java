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

import java.lang.reflect.Field;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
@Deprecated
public class TriedToAccessFieldButWasDeniedException
    extends PermanentException {

  public TriedToAccessFieldButWasDeniedException(IllegalAccessException e, Field field, Object target) {
    super(e, "Not allowed to access '{}' on '{}'", field.getName(), target.getClass().getName());
  }

  public TriedToAccessFieldButWasDeniedException(IllegalArgumentException e, Field field, Object target) {
    super(e, "Field '{}' was not found on target '{}'", field.getName(), target.getClass().getName());
  }

}
