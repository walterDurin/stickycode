package net.stickycode.reflector;

import java.lang.reflect.Method;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class TriedToAccessMethodButWasDeniedException
    extends PermanentException {

  public TriedToAccessMethodButWasDeniedException(IllegalAccessException e, Method method, Object target) {
    super(e, "Not allowed to access '{}' on '{}'", method.getName(), target.getClass().getName());
  }

  public TriedToAccessMethodButWasDeniedException(IllegalArgumentException e, Method method, Object target) {
    super(e, "Field '{}' was not found on target '{}'", method.getName(), target.getClass().getName());
  }
}
