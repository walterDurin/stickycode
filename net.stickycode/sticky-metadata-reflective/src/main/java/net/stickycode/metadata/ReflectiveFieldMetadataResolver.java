package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectiveFieldMetadataResolver
    implements MetadataResolver {

  private Field field;

  public ReflectiveFieldMetadataResolver(Field method) {
    this.field = method;
  }

  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    if (field.isAnnotationPresent(annotation))
      return true;

    for (Annotation a : field.getAnnotations()) {
      if (a.annotationType().isAnnotationPresent(annotation))
        return true;
    }

    return false;
  }

  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    if (field.isAnnotationPresent(annotation))
      return true;

    return false;
  }

}
