package net.stickycode.heartbeat.pulse;

import java.lang.reflect.Method;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class HeartbeatMethodsMustNotHaveAnyParametersException
    extends PermanentException {

  public HeartbeatMethodsMustNotHaveAnyParametersException(Method method) {
    super("Heartbeat methods should not have parameters yet I found {} on {}.{}",
        method.getParameterTypes(), method.getDeclaringClass().getName(), method.getName());

  }

}
