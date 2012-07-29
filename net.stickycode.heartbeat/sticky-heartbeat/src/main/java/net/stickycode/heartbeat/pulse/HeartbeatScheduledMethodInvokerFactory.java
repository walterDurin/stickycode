package net.stickycode.heartbeat.pulse;

import java.beans.Introspector;
import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.heartbeat.HeartbeatRepository;
import net.stickycode.heartbeat.stereotype.Pulse;
import net.stickycode.heartbeat.stereotype.PulseMeasurement;
import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.scheduled.ScheduledMethodInvokerFactory;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class HeartbeatScheduledMethodInvokerFactory
    implements ScheduledMethodInvokerFactory {

  @Inject
  private HeartbeatRepository repository;

  @Inject
  private MetadataResolverRegistry resolver;

  @Override
  public boolean canInvoke(Method method) {
    if (!resolver.is(method).metaAnnotatedWith(Pulse.class))
      return false;

    if (!PulseMeasurement.class.isAssignableFrom(method.getReturnType()))
      throw new MethodsAnnotatedWithPulseMustReturnHeartbeatException(method);

    if (method.getParameterTypes().length > 0)
      throw new HeartbeatMethodsMustNotHaveAnyParametersException(method);

    return true;
  }

  @Override
  public ScheduledRunnable create(Object target, Method method) {
    String label = Introspector.decapitalize(target.getClass().getSimpleName()) + "." + method.getName();
    PulseReadings readings = new PulseReadings(label);
    HeartbeatScheduledMethodInvoker heartbeat = new HeartbeatScheduledMethodInvoker(readings, method, target);
    repository.add(readings);
    return heartbeat;
  }

}
