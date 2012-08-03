package net.stickycode.resource;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class TestResourceConnection
    implements ResourceConnection {

  private InputStream in;

  private OutputStream out;

  public TestResourceConnection(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
  }
  
  public TestResourceConnection(OutputStream out) {
    this.out = out;
  }
  
  public TestResourceConnection(InputStream in) {
    this.in = in;
  }

  @Override
  public InputStream getInputStream() {
    if (in == null)
      throw new IllegalStateException("no in stream for test");

    return in;
  }

  @Override
  public OutputStream getOutputStream() {
    if (out == null)
      throw new IllegalStateException("no out stream for test");

    return out;
  }

  @Override
  public Charset getCharacterSet() {
    return Charset.forName("UTF-8");
  }

  @Override
  public ResourceLocation getLocation() {
    return null;
  }

  @Override
  public void store(Object content) {
  }

}
