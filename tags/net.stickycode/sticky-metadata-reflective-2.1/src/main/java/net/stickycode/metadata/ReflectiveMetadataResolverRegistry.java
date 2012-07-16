package net.stickycode.metadata;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

@StickyComponent
@StickyFramework
public class ReflectiveMetadataResolverRegistry
    implements MetadataResolverRegistry {

  @Override
  public MetadataResolver is(Method method) {
    return new ReflectiveMethodMetadataResolver(method);
  }

  @Override
  public MetadataResolver is(Field field) {
    return new ReflectiveFieldMetadataResolver(field);
  }

  @Override
  public MetadataResolver is(Class<?> annotatedClass) {
    return new ReflectiveTypeMetadataResolver(annotatedClass);
  }

  @Override
  public ElementMetadataResolver does(Class<?> type) {
    return new ReflectiveElementMetadataResolver(type);
  }

  @Override
  public MetadataResolver is(AnnotatedElement annotatedElement) {
    return new ReflectiveAnnotatedElementMetadataResolver(annotatedElement);
  }

}
