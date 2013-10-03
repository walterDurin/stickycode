package net.stickycode.reflector;

import java.lang.reflect.Field;

public class Fields {

  public static void set(Object target, Field field, Object value) {
    boolean accessible = field.isAccessible();
    field.setAccessible(true);
    try {
      safeSetValue(target, field, value);
    }
    finally {
      field.setAccessible(accessible);
    }
  }

  private static void safeSetValue(Object target, Field field, Object value) {
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

  public static <T> T get(Object bean, Field field) {
    boolean accessible = field.isAccessible();
    try {
      field.setAccessible(true);
      return safeGetField(bean, field);
    }
    finally {
      field.setAccessible(accessible);
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> T safeGetField(Object target, Field field) {
    try {
      return (T) field.get(target);
    }
    catch (IllegalArgumentException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
    catch (IllegalAccessException e) {
      throw new TriedToAccessFieldButWasDeniedException(e, field, target);
    }
  }

  /**
   * Find a declared method without checked exceptions
   */
  public static Field find(Class<?> type, String name) {
    try {
      return type.getDeclaredField(name);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
}
