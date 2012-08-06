package net.stickycode.resource.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class StringResourceCodec
    implements ResourceCodec<String> {

  @Configured
  private Integer bufferSize = 2048;

  @Override
  public String load(CoercionTarget resourceTarget, InputStream input, Charset characterSet) {
    try {
      return loadResourceFully(input, characterSet);
    }
    catch (IOException e) {
      throw new ResourceDecodingFailureException(e, resourceTarget.getType(), this);
    }
  }

  @Override
  public void store(CoercionTarget sourceType, String resource, OutputStream output, Charset characterSet) {
    try {
      output.write(resource.getBytes(characterSet));
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String loadResourceFully(InputStream input, Charset characterSet)
      throws IOException {
    Reader reader = new InputStreamReader(input, characterSet);

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
