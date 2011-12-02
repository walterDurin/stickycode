package net.stickycode.metadata;

import java.lang.annotation.Annotation;

public class ReflectiveTypeMetadataResolver
    implements MetadataResolver {

  private Class<?> annotatedClass;

  public ReflectiveTypeMetadataResolver(Class<?> annotatedClass) {
    this.annotatedClass = annotatedClass;
  }

  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    return metaAnnotatedWith(annotation, annotatedClass);
  }

  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    return annotatedWith(annotation, annotatedClass);
  }

  private boolean annotatedWith(Class<? extends Annotation> annotation, Class<?> type) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      if (current.isAnnotationPresent(annotation))
        return true;

      for (Class<?> i : current.getInterfaces()) {
        if (annotatedWith(annotation, i))
          return true;
      }
    }

    return false;
  }

  private boolean metaAnnotatedWith(Class<? extends Annotation> annotation, Class<?> type) {
    for (Class<?> current = type; current != null; current = current.getSuperclass()) {
      if (current.isAnnotationPresent(annotation))
        return true;

      for (Annotation a : current.getAnnotations()) {
        if (a.annotationType().isAnnotationPresent(annotation))
          return true;
      }

      for (Class<?> i : current.getInterfaces()) {
        if (metaAnnotatedWith(annotation, i))
          return true;
      }
    }

    return false;
  }

}
