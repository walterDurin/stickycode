package net.stickycode.heartbeat.pulse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.configured.ConfigurationMetadataProcessor;
import net.stickycode.heartbeat.stereotype.PulseMeasurement;
import net.stickycode.heartbeat.stereotype.Reading;
import net.stickycode.reflector.Methods;
import net.stickycode.scheduled.MethodConfigurationTarget;
import net.stickycode.scheduled.PeriodicSchedule;
import net.stickycode.scheduled.Schedule;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.configured.Configured;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatScheduledMethodInvoker
    implements ScheduledRunnable {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Configured("The regularity of the checking of this heartbeat")
  private Schedule schedule = new PeriodicSchedule(1, TimeUnit.MINUTES);

  private Method method;

  private Object target;

  private PulseReadings readings;

  private MethodConfigurationTarget name;

  public HeartbeatScheduledMethodInvoker(PulseReadings readings, Method method, Object target) {
    this.readings = readings;
    this.method = method;
    this.target = target;
    name = new MethodConfigurationTarget(method);
  }

  @Override
  public void run() {
    readings.add(checkPulse());
  }

  protected Reading checkPulse() {
    log.debug("taking pulse {}", this);
    PulseReader reader = new PulseReader();
    try {
      return reader.take(invoke());
    }
    catch (Exception e) {
      log.warn("Failed to ascertain pulse of {} due to {}", this, e.getMessage());
      return reader.fail(e);
    }
    catch (Throwable t) {
      log.error("Failed to ascertain pulse of {} because...", t);
      return reader.fail(t);
    }
  }

  protected PulseMeasurement invoke() throws IllegalAccessException, InvocationTargetException {
    return Methods.invoke(target, method, new Object[0]);
  }

  public Schedule getSchedule() {
    return schedule;
  }

  @Override
  public String toString() {
    return readings.getLabel();
  }

  @Override
  public void applyCoercion(CoercionFinder coercions) {
  }

  @Override
  public void update() {
  }

  @Override
  public void invertControl(ComponentContainer container) {
  }

  @Override
  public void recurse(ConfigurationMetadataProcessor processor) {
    processor.process(name, this);
  }

  @Override
  public boolean requiresResolution() {
    return true;
  }

  @Override
  public ResolvedConfiguration getResolution() {
    return null;
  }

  @Override
  public Object getTarget() {
    return target;
  }

  @Override
  public void resolvedWith(ResolvedConfiguration resolved) {
  }

  @Override
  public CoercionTarget getCoercionTarget() {
    return null;
  }

  @Override
  public String join(String delimeter) {
    return name.join(delimeter);
  }

}
