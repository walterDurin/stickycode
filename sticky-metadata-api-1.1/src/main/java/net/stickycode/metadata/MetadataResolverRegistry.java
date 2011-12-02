package net.stickycode.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface MetadataResolverRegistry {

  /**
   * return a resolver for the given method that can resolve annotations on
   * <ol>
   * <li>the method itself</li>
   * <li>annotations on the method</li>
   * </ol>
   */
  MetadataResolver method(Method method);

  /**
   * return a resolver for the given field that can resolve annotations on
   * <ol>
   * <li>the field itself</li>
   * <li>annotations on the field</li>
   * </ol>
   */
  MetadataResolver field(Field field);

  /**
   * return a resolver for the given class that resolves annotations on
   * <ol>
   * <li>the type itself</li>
   * <li>interfaces of the type</li>
   * <li>any of the supertypes</li>
   * <li>any of the interfaces of the super types</li>
   * </ol>
   */
  MetadataResolver is(Class<?> annotatedClass);

}
