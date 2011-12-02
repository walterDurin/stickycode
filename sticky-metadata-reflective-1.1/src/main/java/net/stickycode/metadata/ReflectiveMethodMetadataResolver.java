package net.stickycode.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectiveMethodMetadataResolver
    implements MetadataResolver {

  private Method method;

  public ReflectiveMethodMetadataResolver(Method method) {
    this.method = method;
  }

  @Override
  public boolean metaAnnotatedWith(Class<? extends Annotation> annotation) {
    if (method.isAnnotationPresent(annotation))
      return true;
    
    for (Annotation a : method.getAnnotations()) {
      if (a.annotationType().isAnnotationPresent(annotation))
        return true;
    }
    
    return false;
  }
  
  @Override
  public boolean annotatedWith(Class<? extends Annotation> annotation) {
    if (method.isAnnotationPresent(annotation))
      return true;
    
    return false;
  }

}
