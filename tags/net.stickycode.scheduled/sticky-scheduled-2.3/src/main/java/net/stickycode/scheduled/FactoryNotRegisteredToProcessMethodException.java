package net.stickycode.scheduled;

import java.lang.reflect.Method;
import java.util.List;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class FactoryNotRegisteredToProcessMethodException
    extends PermanentException {

  public FactoryNotRegisteredToProcessMethodException(Object target, Method method,
      List<? extends ScheduledMethodInvokerFactory> methodInvokerFactories) {
    super(
        "When trying to register {}.{} for invocation, found that none of the factories {} were applicable."
            + " This means that the field was annotated witha scheduling annotation but the relevant factory " +
            "is not set up to invoke it."
        , target.getClass().getName(), method.getName(), methodInvokerFactories);
  }
}
