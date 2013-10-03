package net.stickycode.metadata;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.stickycode.stereotype.StickyFramework;

/**
 * 
 * <pre>
 * Inject
 * MetadataResolverRegisty usingMetadata;
 * 
 * ...
 * 
 * Field field = findField();
 * if (withMetadata.is(field).metaAnnotatedWith(Configured.class)
 *   doSomethingWithAnnotatedField(field);
 *   
 * ...
 * 
 * Method method = fieldMethod();
 * if (usingMetadata.is(method).metaAnnotatedWith(Scheduled.class)
 *   doSomethingWithAnnotatedMethod(method);
 * 
 * ...
 * 
 * Class<?> type = findType();
 * if (usingMetadata.is(type).annotatedWith(StickRepository.class)
 *   doSomethingWithAnnoatedType(type);
 *   
 * ...
 * 
 * Class<?> type = findType();
 * if (usingMetadata.does(type).haveAnyFieldsAnnotatedWith(Configured.class, ConfiguredStrategy.class)
 *   doSomethingWithTypeWithAnnotedElements();
 *   
 * ...
 * 
 * Class<?> type = findType();
 * if (usingMetadata.does(type).haveAnyMethodsAnnotatedWith(Scheduled.class, Pulse.class)
 *   doSomethingWithTypeWithAnnotedElements();
 * </pre>
 */
@StickyFramework
public interface MetadataResolverRegistry {

  /**
   * return a resolver for the given method that can resolve annotations on
   * <ol>
   * <li>the method itself</li>
   * <li>annotations on the method</li>
   * </ol>
   */
  MetadataResolver is(Method method);

  /**
   * return a resolver for the given field that can resolve annotations on
   * <ol>
   * <li>the field itself</li>
   * <li>annotations on the field</li>
   * </ol>
   */
  MetadataResolver is(Field field);

  /**
   * return a resolver for the given class that can resolve annotations on
   * <ol>
   * <li>the type itself</li>
   * <li>interfaces of the type</li>
   * <li>any of the supertypes</li>
   * <li>any of the interfaces of the super types</li>
   * </ol>
   */
  MetadataResolver is(Class<?> annotatedClass);

  /**
   * return a resolver for the given class that can resolve annotations on
   * <ol>
   * <li>methods or fields of the type itself</li>
   * <li>methods or fileds of the interfaces of the type</li>
   * <li>methods or fields of any of the supertypes</li>
   * <li>methods or fields of any of the interfaces of the super types</li>
   * </ol>
   */
  ElementMetadataResolver does(Class<?> type);

  /**
   * return a resolver for the given annotation element that can resolve annotations on
   * <ol>
   * <li>the element itself</li>
   * <li>any of the super elements</li>
   * </ol>
   */
  MetadataResolver is(AnnotatedElement annotatedElement);

}
