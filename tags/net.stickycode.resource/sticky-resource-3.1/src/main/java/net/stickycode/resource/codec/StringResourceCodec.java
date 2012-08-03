package net.stickycode.resource.codec;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceConnection;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class StringResourceCodec
    implements ResourceCodec<String> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Configured
  private Integer bufferSize = 2048;

  @Override
  public String load(ResourceConnection connection, CoercionTarget target) {
    try {
      return loadResourceFully(connection);
    }
    catch (IOException e) {
      throw new ResourceDecodingFailureException(e, target.getType(), this);
    }
  }

  @Override
  public void store(CoercionTarget sourceType, String resource, ResourceConnection connection) {
    throw new UnsupportedOperationException("Not implemented");
  }

  private String loadResourceFully(ResourceConnection connection) throws IOException {
    Reader reader = new InputStreamReader(connection.getInputStream(), connection.getCharacterSet());

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

  @Override
  public String getDefaultFileSuffix() {
    return ".txt";
  }

}
