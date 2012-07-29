package net.stickycode.heartbeat.gauge;

import java.lang.reflect.Method;

import net.stickycode.exception.PermanentException;
import net.stickycode.heartbeat.stereotype.Gauge;
import net.stickycode.heartbeat.stereotype.GaugeReader;

@SuppressWarnings("serial")
public class GaugeMethodsMustHaveGaugeReaderParametersException
    extends PermanentException {

  public GaugeMethodsMustHaveGaugeReaderParametersException(Method method) {
    super("Method {} annotated with {} must return {} and take {} as a parameter",
        method, Gauge.class, GaugeReading.class, GaugeReader.class);
  }

}
