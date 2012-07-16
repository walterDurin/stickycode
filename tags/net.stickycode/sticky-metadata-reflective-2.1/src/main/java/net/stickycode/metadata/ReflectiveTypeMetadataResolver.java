package net.stickycode.metadata;

import java.lang.annotation.Annotation;

import net.stickycode.reflector.predicate.PredicateReflector;

public class ReflectiveTypeMetadataResolver
    implements MetadataResolver {

  private Class<?> annotatedClass;

  public ReflectiveTypeMetadataResolver(Class<?> annotatedClass) {
    this.annotatedClass = annotatedClass;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    return new PredicateReflector().given(annotatedClass).contractIs(new MetaAnnotatedElementPredicate(annotation));
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    return new PredicateReflector().given(annotatedClass).contractIs(new AnnotatedElementPredicate(annotation));
  }

}
