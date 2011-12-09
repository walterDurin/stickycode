package net.stickycode.reflector.predicate;

import java.lang.reflect.Field;

/**
 * A rule that evaluates to true when applied to a field.
 */
public interface FieldPredicate {

  /**
   * Evaluate this condition for the given field
   */
  boolean apply(Field m);

}
