package net.stickycode.resource.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.component.StickyExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class StringResourceCodec
    implements ResourceCodec<String> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Configured
  private Integer bufferSize = 2048;

  @Override
  public String load(InputStream in, CoercionTarget target) {
    return asString(in);
  }

  @Override
  public void store(CoercionTarget sourceType, String resource, OutputStream out) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public String asString(InputStream is) {
    try {
      return loadResourceFully(is);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    finally {
      try {
        is.close();
      }
      catch (IOException e) {
        log.error("Failed to close resource stream for '{}' caused by {}", e.getMessage());
        log.debug("Stack trace of resource '{}' close failure", e);
      }
    }
  }

  private String loadResourceFully(InputStream is) throws IOException {
    Reader reader = new InputStreamReader(is, Charset.forName("UTF-8"));

    char[] buffer = new char[bufferSize];
    int count = reader.read(buffer);
    if (count < bufferSize)
      return new String(buffer, 0, count);

    StringBuilder out = new StringBuilder();

    while (count > 0) {
      out.append(buffer, 0, count);
      count = reader.read(buffer);
    }

    return out.toString();
  }

  protected void append(StringBuilder out, String line) {
    out.append("\n").append(line);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(String.class);
  }

}
