package net.stickycode.heartbeat.gauge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.coercion.CoercionFinder;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.configured.ConfigurationMetadataProcessor;
import net.stickycode.heartbeat.stereotype.Reading;
import net.stickycode.scheduled.MethodConfigurationTarget;
import net.stickycode.scheduled.PeriodicSchedule;
import net.stickycode.scheduled.Schedule;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.configured.Configured;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaugeScheduledMethodInvoker
    implements ScheduledRunnable {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Method method;

  private Object target;

  @Configured("The regularity with which this gauge should be read")
  private Schedule schedule = new PeriodicSchedule(5, TimeUnit.MINUTES);

  private GaugeReadings readings;

  private MethodConfigurationTarget name;

  public GaugeScheduledMethodInvoker(GaugeReadings readings, Method method, Object target) {
    this.readings = readings;
    this.method = method;
    this.target = target;
    this.name = new MethodConfigurationTarget(method);
  }

  @Override
  public void run() {
    readings.add(takeReading());
  }

  protected Reading takeReading() {
    log.debug("reading gauge {}", this);
    HeartbeatGaugeReader reader = new HeartbeatGaugeReader();
    try {
      return (Reading) method.invoke(target, new Object[] { reader });
    }
    catch (InvocationTargetException e) {
      log.warn("Failed to ascertain pulse of {} due to {}", this, e.getMessage());
      return reader.fail(e);
    }
    catch (Throwable t) {
      log.error("Failed to ascertain pulse of {} because...", t);
      return reader.fail(t);
    }
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
