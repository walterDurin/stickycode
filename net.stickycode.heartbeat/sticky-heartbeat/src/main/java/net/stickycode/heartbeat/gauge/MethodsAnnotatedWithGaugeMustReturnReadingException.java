package net.stickycode.heartbeat.gauge;

import java.lang.reflect.Method;

import net.stickycode.exception.PermanentException;
import net.stickycode.heartbeat.stereotype.Gauge;

@SuppressWarnings("serial")
public class MethodsAnnotatedWithGaugeMustReturnReadingException
    extends PermanentException {

  public MethodsAnnotatedWithGaugeMustReturnReadingException(Method method) {
    super("Method {} annotated with {} must return a type of {}", method, Gauge.class, GaugeReading.class);
  }

}
