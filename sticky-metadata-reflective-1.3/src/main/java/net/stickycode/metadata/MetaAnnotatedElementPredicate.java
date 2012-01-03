package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.stickycode.reflector.predicate.FieldPredicate;
import net.stickycode.reflector.predicate.MethodPredicate;
import net.stickycode.reflector.predicate.TypePredicate;

public class MetaAnnotatedElementPredicate
    implements MethodPredicate, FieldPredicate, TypePredicate {

  private Class<? extends Annotation>[] annotations;

  public MetaAnnotatedElementPredicate(Class<? extends Annotation>... annotations) {
    this.annotations = annotations;
  }

  @Override
  public boolean apply(Method method) {
    return isMetaAnnotated(method);
  }

  @Override
  public boolean apply(Class<?> type) {
    return isMetaAnnotated(type);
  }

  @Override
  public boolean apply(Field field) {
    return isMetaAnnotated(field);
  }

  public boolean apply(AnnotatedElement annotatedElement) {
    return isMetaAnnotated(annotatedElement);
  }

  private boolean isMetaAnnotated(AnnotatedElement element) {
    for (Class<? extends Annotation> a : annotations) {
      if (element.isAnnotationPresent(a))
        return true;

      for (Annotation annotation : element.getAnnotations()) {
        if (annotation.annotationType().isAnnotationPresent(a))
          return true;
      }
    }
    return false;
  }

}
