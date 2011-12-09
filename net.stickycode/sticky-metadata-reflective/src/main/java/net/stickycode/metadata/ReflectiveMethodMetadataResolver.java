package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectiveMethodMetadataResolver
    implements MetadataResolver {

  private Method method;

  public ReflectiveMethodMetadataResolver(Method method) {
    this.method = method;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    return new MetaAnnotatedElementPredicate(annotation).apply(method);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    return new AnnotatedElementPredicate(annotation).apply(method);
  }

}
