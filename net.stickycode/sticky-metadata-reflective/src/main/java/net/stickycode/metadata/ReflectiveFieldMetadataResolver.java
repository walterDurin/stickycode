package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectiveFieldMetadataResolver
    implements MetadataResolver {

  private Field field;

  public ReflectiveFieldMetadataResolver(Field method) {
    this.field = method;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    return new MetaAnnotatedElementPredicate(annotation).apply(field);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    return new AnnotatedElementPredicate(annotation).apply(field);
  }

}
