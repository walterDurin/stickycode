package net.stickycode.reflector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Methods {

  public static <T> T invoke(Object target, Class<?> type, String method, Object... values) {
    return invoke(target, find(type, method), values);
  }

  public static <T> T invoke(Object target, String method, Object... values) {
    return invoke(target, find(target.getClass(), method), values);
  }

  public static <T> T invoke(Object target, Method method, Object[] values) {
    boolean accessible = method.isAccessible();
    method.setAccessible(true);
    try {
      return safeSetValue(target, method, values);
    }
    finally {
      method.setAccessible(accessible);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T safeSetValue(Object target, Method method, Object[] values) {
    try {
      return (T) method.invoke(target, values);
    }
    catch (IllegalArgumentException e) {
      throw new TriedToAccessMethodButWasDeniedException(e, method, target);
    }
    catch (IllegalAccessException e) {
      throw new TriedToAccessMethodButWasDeniedException(e, method, target);
    }
    catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Find a declared method without checked exceptions
   */
  public static Method find(Class<?> type, String name) {
    try {
      return type.getDeclaredMethod(name);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}
