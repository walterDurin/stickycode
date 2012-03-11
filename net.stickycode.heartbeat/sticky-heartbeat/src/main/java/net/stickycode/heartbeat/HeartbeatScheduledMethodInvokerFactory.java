package net.stickycode.heartbeat;

import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.scheduled.ScheduledMethodInvokerFactory;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.Pulse;
import net.stickycode.stereotype.component.StickyExtension;

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

    if (!Boolean.class.isAssignableFrom(method.getReturnType()))
      throw new MethodsAnnotatedWithPulseMustReturnHeartbeatException(method);

    if (method.getParameterTypes().length > 0)
      throw new HeartbeatMethodsMustNotHaveAnyParametersException(method);

    return true;
  }

  @Override
  public ScheduledRunnable create(Object target, Method method) {
    HeartbeatScheduledMethodInvoker heartbeat = new HeartbeatScheduledMethodInvoker(method, target);
    repository.add(heartbeat);
    return heartbeat;
  }

}
