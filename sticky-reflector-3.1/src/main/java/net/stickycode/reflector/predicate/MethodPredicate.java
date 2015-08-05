package net.stickycode.reflector.predicate;

import java.lang.reflect.Method;

/**
 * A rule that evaluates to true when applied to a method.
 */
public interface MethodPredicate {

  /**
   * Evaluate this condition for the given method
   */
  boolean apply(Method method);

}
