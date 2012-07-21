package net.stickycode.configured.finder;

import java.util.Collection;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class BeanNotFoundException
    extends PermanentException {

  public BeanNotFoundException(Exception e, Class<?> type) {
    super(e, "Bean of type {} not bound to the application context", type);
  }

  public <T> BeanNotFoundException(Class<T> type, Collection<T> beans) {
    super("Bean of type {} was bound more than once in the application context but only one is desired. Found {}", type, beans);
  }

}
