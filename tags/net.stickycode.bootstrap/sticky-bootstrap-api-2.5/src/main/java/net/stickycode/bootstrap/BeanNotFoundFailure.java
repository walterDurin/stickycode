package net.stickycode.bootstrap;

import static net.stickycode.stereotype.failure.FailureClassification.Container;

import java.util.Collection;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.ParameterisedFailure;

@SuppressWarnings("serial")
@Failure(Container)
public class BeanNotFoundFailure
    extends ParameterisedFailure {

  public BeanNotFoundFailure(Exception e, Class<?> type) {
    super(e, "Bean of type {} not bound to the application context", type);
  }

  public <T> BeanNotFoundFailure(Class<T> type, Collection<T> beans) {
    super("Bean of type {} was bound more than once in the application context but only one is desired. Found {}", type, beans);
  }

}
