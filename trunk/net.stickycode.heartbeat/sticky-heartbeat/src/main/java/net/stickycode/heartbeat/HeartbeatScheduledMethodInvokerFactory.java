package net.stickycode.heartbeat;

import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.heartbeat.HeartbeatRepository;
import net.stickycode.metadata.TypeMetadataResolver;
import net.stickycode.scheduled.ScheduleConfiguration;
import net.stickycode.scheduled.ScheduledMethodInvokerFactory;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.Pulse;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
@StickyFramework
public class HeartbeatScheduledMethodInvokerFactory
    implements ScheduledMethodInvokerFactory {

  @Inject
  private HeartbeatRepository repository;
  
  @Inject
  TypeMetadataResolver resolver;
  
  @Override
  public boolean canInvoke(Method method) {
    if (!resolver.method(method).has(Pulse.class))
      return false;

    if (!method.getReturnType().isAssignableFrom(Boolean.class))
      throw new MethodsAnnotatedWithPulseMustReturnHeartbeatException(method);

    if (method.getParameterTypes().length > 0)
      throw new HeartbeatMethodsMustNotHaveAnyParametersException(method);

    return true;
  }

  @Override
  public ScheduledRunnable create(Object target, Method method, ScheduleConfiguration schedule) {
    HeartbeatScheduledMethodInvoker heartbeat = new HeartbeatScheduledMethodInvoker(method, target, schedule);
    repository.add(heartbeat);
    return heartbeat;
  }

}
