package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.stickycode.reflector.predicate.FieldPredicate;
import net.stickycode.reflector.predicate.MethodPredicate;
import net.stickycode.reflector.predicate.TypePredicate;

public class AnnotatedElementPredicate
    implements MethodPredicate, FieldPredicate, TypePredicate {

  private Class<? extends Annotation>[] annotations;

  @SafeVarargs
  public AnnotatedElementPredicate(Class<? extends Annotation>... annotations) {
    this.annotations = annotations;
  }

  @Override
  public boolean apply(Method method) {
    return isAnnotated(method);
  }

  @Override
  public boolean apply(Class<?> type) {
    return isAnnotated(type);
  }

  @Override
  public boolean apply(Field field) {
    return isAnnotated(field);
  }

  public boolean apply(AnnotatedElement element) {
    return isAnnotated(element);
  }

  private boolean isAnnotated(AnnotatedElement element) {
    for (Class<? extends Annotation> a : annotations) {
      if (element.isAnnotationPresent(a))
        return true;
    }

    return false;
  }

}
