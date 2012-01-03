package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class ReflectiveAnnotatedElementMetadataResolver
    implements MetadataResolver {

  private AnnotatedElement annotatedElement;

  public ReflectiveAnnotatedElementMetadataResolver(AnnotatedElement element) {
    this.annotatedElement = element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    return new MetaAnnotatedElementPredicate(annotation).apply(annotatedElement);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    return new AnnotatedElementPredicate(annotation).apply(annotatedElement);
  }

}
