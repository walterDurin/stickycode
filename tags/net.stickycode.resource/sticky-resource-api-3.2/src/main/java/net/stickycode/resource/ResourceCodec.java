package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;

/**
 * Contract for algorithm's that can encode and decode streams into particular targets, where a target is a field or other type.
 */
public interface ResourceCodec<T> {

  /**
   * Load a T from a stream using the given type information
   */
  T load(CoercionTarget resourceTarget, InputStream input, Charset characterSet);

  /**
   * Store a T to the stream using the given type information
   */
  void store(CoercionTarget sourceType, T resource, OutputStream output, Charset characterSet);

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
