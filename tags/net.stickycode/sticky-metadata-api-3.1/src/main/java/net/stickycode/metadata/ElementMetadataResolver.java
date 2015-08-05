package net.stickycode.metadata;

import java.lang.annotation.Annotation;

/**
 * Resolver contract for fluently querying annotation metadata of the elements of the subject of resolution
 */
public interface ElementMetadataResolver {

  /**
   * Are any methods of the subject of resolution annotated with one of the given annotations
   */
  boolean haveAnyMethodsAnnotatedWith(Class<? extends Annotation>... annotations);

  /**
   * Are any methods of the subject of resolution annotated with one of the given annotations or annotations annotated by the given
   * annotations
   */
  boolean haveAnyMethodsMetaAnnotatedWith(Class<? extends Annotation>... annotations);

  /**
   * Are any fields of the subject of resolution annotated with one of the given annotations
   */
  boolean haveAnyFieldsAnnotatedWith(Class<? extends Annotation>... annotations);

  /**
   * Are any fields of the subject of resolution annotated with one of the given annotations or annotations annotated by the given
   * annotations
   */
  boolean haveAnyFieldsMetaAnnotatedWith(Class<? extends Annotation>... annotations);

}
