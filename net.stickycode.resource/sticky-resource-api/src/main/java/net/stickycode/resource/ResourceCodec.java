package net.stickycode.resource;

import net.stickycode.coercion.CoercionTarget;

/**
 * Contract for algorithm's that can encode and decode streams into particular targets, where a target is a field or other type.
 */
public interface ResourceCodec<T> {

  /**
   * Load a T from a stream using the given type information
   */
  T load(ResourceConnection source, CoercionTarget targetType);

  /**
   * Store a T to the stream using the given type information
   */
  void store(CoercionTarget sourceType, T resource, ResourceConnection target);

  /**
   * @return true if this codec is applicable for the given type.
   */
  boolean isApplicableTo(CoercionTarget type);

  /**
   * The common suffix for files this codec can process.
   * e.g. String codecs would have a suffix of .txt, Xml codecs a suffix of .xml
   */
  String getDefaultFileSuffix();

}
