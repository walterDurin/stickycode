package net.stickycode.heartbeat.gauge;

import java.beans.Introspector;
import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.heartbeat.GaugeRepository;
import net.stickycode.heartbeat.stereotype.Gauge;
import net.stickycode.heartbeat.stereotype.GaugeReader;
import net.stickycode.heartbeat.stereotype.Reading;
import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.scheduled.ScheduledMethodInvokerFactory;
import net.stickycode.scheduled.ScheduledRunnable;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class GaugeScheduledMethodInvokerFactory
    implements ScheduledMethodInvokerFactory {

  @Inject
  private GaugeRepository repository;
  
  @Inject
  private MetadataResolverRegistry resolver;
  
  @Override
  public boolean canInvoke(Method method) {
    if (!resolver.is(method).metaAnnotatedWith(Gauge.class))
      return false;

    if (!Reading.class.isAssignableFrom(method.getReturnType()))
      throw new MethodsAnnotatedWithGaugeMustReturnReadingException(method);

    if (method.getParameterTypes().length != 1)
      throw new GaugeMethodsMustHaveGaugeReaderParametersException(method);
    
    if (!GaugeReader.class.isAssignableFrom(method.getParameterTypes()[0]))
      throw new GaugeMethodsMustHaveGaugeReaderParametersException(method);

    return true;
  }

  @Override
  public ScheduledRunnable create(Object target, Method method) {
    String label = Introspector.decapitalize(target.getClass().getSimpleName()) + "." + method.getName();
    GaugeReadings readings = new GaugeReadings(label);
    GaugeScheduledMethodInvoker heartbeat = new GaugeScheduledMethodInvoker(readings, method, target);
    repository.add(readings);
    return heartbeat;
  }

}
