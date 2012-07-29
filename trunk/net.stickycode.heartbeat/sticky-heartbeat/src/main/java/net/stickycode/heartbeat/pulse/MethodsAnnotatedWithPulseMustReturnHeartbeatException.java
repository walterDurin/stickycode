package net.stickycode.heartbeat.pulse;

import java.lang.reflect.Method;

import net.stickycode.exception.PermanentException;


public class MethodsAnnotatedWithPulseMustReturnHeartbeatException
    extends PermanentException {

  public MethodsAnnotatedWithPulseMustReturnHeartbeatException(Method method) {
    super("Heartbeat methods {}.{} must return boolean", method.getDeclaringClass().getName(), method.getName());
  }

}
