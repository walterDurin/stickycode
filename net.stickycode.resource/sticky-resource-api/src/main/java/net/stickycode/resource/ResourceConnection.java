package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public interface ResourceConnection<T> {

  /**
   * Get the stream that the resource can be read from
   */
  InputStream getInputStream();

  /**
   * Get the stream that the resource can be written to
   */
  OutputStream getOutputStream();
  
  /**
   * Store the resource using this connection
   */
  void store(T content);

  /**
   * The character encoding of the stream if it makes sense to have one
   */
  Charset getCharacterSet();

  /**
   * Return the location specification of the resource
   */
  ResourceLocation getLocation();

}
