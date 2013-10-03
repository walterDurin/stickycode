package net.stickycode.reflector.predicate;

/**
 * A rule that evaluates to true when applied to a type
 */
public interface TypePredicate {

  /**
   * Evaluate this condition for the given type
   */
  boolean apply(Class<?> type);

}
