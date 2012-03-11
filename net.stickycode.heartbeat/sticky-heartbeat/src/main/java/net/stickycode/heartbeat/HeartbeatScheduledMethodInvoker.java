package net.stickycode.heartbeat;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.stickycode.scheduled.Schedule;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.Configured;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatScheduledMethodInvoker
    implements ScheduledRunnable, Heartbeat {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Method method;

  private Object target;

  @Configured
  private Schedule schedule;

  private DateTime lastAttempt;

  private DateTime lastSuccess;

  private DateTime lastFailure;

  private Boolean alive = true;

  private Object $this = new Object();

  public HeartbeatScheduledMethodInvoker(Method method, Object target) {
    this.method = method;
    this.target = target;
  }

  @Override
  public void run() {
    synchronized ($this) {
      try {
        invoke();
      }
      catch (Exception e) {
        lastFailure = new DateTime();
        log.warn("Failed to ascertain pulse of {} due to {}", this, e.getMessage());
        log.debug("Failed to ascertain pulse of {} because...", e);
      }
      catch (Throwable t) {
        lastFailure = new DateTime();
        log.error("Failed to ascertain pulse of {} because...", t);
      }
    }
  }

  protected void invoke() throws IllegalAccessException, InvocationTargetException {
    lastAttempt = new DateTime();
    alive = (Boolean) method.invoke(target, new Object[0]);
    lastSuccess = new DateTime();
  }

  public Schedule getSchedule() {
    return schedule;
  }

  @Override
  public boolean isAlive() {
    return alive;
  }

  @Override
  public String getLabel() {
    return Introspector.decapitalize(target.getClass().getSimpleName()) + "." + method.getName();
  }

}
