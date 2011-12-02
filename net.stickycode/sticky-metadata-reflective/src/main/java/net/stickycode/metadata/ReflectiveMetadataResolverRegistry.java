package net.stickycode.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

@StickyComponent
@StickyFramework
public class ReflectiveMetadataResolverRegistry
    implements MetadataResolverRegistry {

  @Override
  public MetadataResolver method(Method method) {
    return new ReflectiveMethodMetadataResolver(method);
  }

  @Override
  public MetadataResolver field(Field field) {
    return new ReflectiveFieldMetadataResolver(field);
  }

  @Override
  public MetadataResolver is(Class<?> annotatedClass) {
    return new ReflectiveTypeMetadataResolver(annotatedClass);
  }

}
