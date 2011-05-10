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

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.stickycode.coercion.AbstractCoercionType;

public class ConfiguredField
    extends AbstractCoercionType
    implements ConfigurationAttribute {

  private final Object defaultValue;
  private final Object target;
  private final Field field;

  public ConfiguredField(Object target, Field field) {
    this.defaultValue = getValue(target, field);
    this.target = target;
    this.field = field;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public Object getValue() {
    return getValue(target, field);
  }

  public String getCategory() {
    return Introspector.decapitalize(target.getClass().getSimpleName());
  }

  @Override
  public Class<?> getType() {
    return field.getType();
  }

  public boolean hasDefaultValue() {
    return defaultValue != null;
  }

  private Object getValue(Object target, Field field) {
    boolean accessible = field.isAccessible();
    try {
      field.setAccessible(true);
      return safeGetField(target, field);
    }
    finally {
      field.setAccessible(accessible);
    }
  }

  private Object safeGetField(Object target, Field field) {
    try {
      return field.get(target);
    }
    catch (IllegalArgumentException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
    catch (IllegalAccessException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
  }

  public void setValue(Object value) {
    boolean accessible = field.isAccessible();
    field.setAccessible(true);
    try {
      safeSetValue(value);
    }
    finally {
      field.setAccessible(accessible);
    }
  }

  private void safeSetValue(Object value) {
    try {
      field.set(target, value);
    }
    catch (IllegalArgumentException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
    catch (IllegalAccessException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
  }

  @Override
  public boolean isGenericType() {
    return field.getGenericType() instanceof ParameterizedType;
  }

  @Override
  public ParameterizedType getGenericType() {
    Type type = field.getGenericType();
    if (type instanceof ParameterizedType)
      return (ParameterizedType) type;

    throw new IllegalStateException("Field is not parameterised");
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public String toString() {
    return String.format("ConfiguredField{'%s' on '%s'}", getName(), getCategory());
  }

  @Override
  public Object getTarget() {
    return target;
  }

}
