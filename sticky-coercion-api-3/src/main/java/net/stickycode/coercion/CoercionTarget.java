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
package net.stickycode.coercion;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Map;

/**
 * Contract for something that can be the target of a coercion.
 * 
 */
public interface CoercionTarget {

  /**
   * Return the type for the target to be coerced
   */
  Class<?> getType();

  /**
   * Is the underlying target an array
   */
  boolean isArray();

  /**
   * Does the target have components, this is true of any container e.g. {@link Collection}, {@link Map}
   * 
   */
  boolean hasComponents();

  /**
   * Return the {@link CoercionTarget}s for the components.
   * 
   * For a collection this would return a single {@link CoercionTarget} for the element contained.
   * 
   * For a map you would get one for the key and one for the value.
   */
  CoercionTarget[] getComponentCoercionTypes();

  /**
   * Return true if this target is a primitive type
   */
  boolean isPrimitive();

  /**
   * If this target represents a primitive type return the boxing type for it
   */
  Class<?> boxedType();

  boolean canBeAnnotated();

  AnnotatedElement getAnnotatedElement();

  /**
   * Return the owning type of the target, for a method this is the class the method was found on similarly for fields.
   */
  Class<?> getOwner();

  CoercionTarget getParent();

  boolean hasParent();

  String getName();

}
