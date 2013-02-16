package net.stickycode.reflector.predicate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * A predicate evaluation carried out with reflection.
 */
public class PredicateReflector {

  private Class<?> type;

  /**
   * Define the type to query
   */
  public PredicateReflector given(Class<?> type) {
    this.type = type;
    return this;
  }

  /**
   * Return true if the type represented by this reflector matches the given predicate
   */
  public boolean isDirectly(TypePredicate condition) {
    return condition.apply(type);
  }

  /**
   * Return true if the type or one of its supertypes represented by this reflector matches the given predicate
   */
  public boolean hierachyIs(TypePredicate condition) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      if (condition.apply(current))
        return true;
    }

    return false;
  }

  /**
   * Return true if the type or one of its supertypes or any interface implemented by the type or one of its supertypes represented
   * by this reflector matches the given predicate
   */
  public boolean contractIs(TypePredicate condition) {
    return contractIs(condition, type);
  }

  protected boolean contractIs(TypePredicate condition, Class<?> base) {
    for (Class<?> current = base; current != null; current = current.getSuperclass()) {
      if (condition.apply(current))
        return true;

      for (Class<?> i : current.getInterfaces()) {
        if (contractIs(condition, i))
          return true;
      }
    }

    return false;
  }

  /**
   * Return true if any method of the {@link #given(Class)} type or one of its supertypes matches the given predicate
   */
  public boolean areAnyMethods(MethodPredicate condition) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      for (Method m : current.getDeclaredMethods()) {
        if (condition.apply(m))
          return true;
      }
    }

    return false;
  }

  /**
   * Return true if all method of the {@link #given(Class)} type or one of its supertypes matches the given predicate
   */
  public boolean areAllMethods(MethodPredicate condition) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      for (Method m : current.getDeclaredMethods()) {
        if (!condition.apply(m))
          return false;
      }
    }

    return true;
  }

  /**
   * Return true if any field of the {@link #given(Class)} type or one of its supertypes matches the given predicate
   */
  public boolean areAnyFields(FieldPredicate condition) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      for (Field m : current.getDeclaredFields()) {
        if (condition.apply(m))
          return true;
      }
    }

    return false;
  }

  /**
   * Return true if all fields of the {@link #given(Class)} type or one of its supertypes matches the given predicate
   */
  public boolean areAllFields(FieldPredicate condition) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      for (Field m : current.getDeclaredFields()) {
        if (!condition.apply(m))
          return false;
      }
    }

    return true;
  }

}
