package net.stickycode.metadata;

import java.lang.annotation.Annotation;

import net.stickycode.reflector.predicate.PredicateReflector;

public class ReflectiveElementMetadataResolver
    implements ElementMetadataResolver {

  private Class<?> type;

  public ReflectiveElementMetadataResolver(Class<?> type) {
    this.type = type;
  }

  @Override
  public boolean haveAnyMethodsAnnotatedWith(Class<? extends Annotation>... annotations) {
    return new PredicateReflector().given(type).areAnyMethods(new AnnotatedElementPredicate(annotations));
  }

  @Override
  public boolean haveAnyMethodsMetaAnnotatedWith(Class<? extends Annotation>... annotations) {
    return new PredicateReflector().given(type).areAnyMethods(new MetaAnnotatedElementPredicate(annotations));
  }

  @Override
  public boolean haveAnyFieldsAnnotatedWith(Class<? extends Annotation>... annotations) {
    return new PredicateReflector().given(type).areAnyFields(new AnnotatedElementPredicate(annotations));
  }

  @Override
  public boolean haveAnyFieldsMetaAnnotatedWith(Class<? extends Annotation>... annotations) {
    return new PredicateReflector().given(type).areAnyFields(new MetaAnnotatedElementPredicate(annotations));
  }

}
