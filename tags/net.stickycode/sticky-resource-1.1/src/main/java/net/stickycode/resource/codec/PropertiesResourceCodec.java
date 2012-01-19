package net.stickycode.resource.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.stickycode.resource.ResourceCodec;


public class PropertiesResourceCodec
    implements ResourceCodec<Properties> {

  @Override
  public Properties load(InputStream input) {
    Properties p = new Properties();
    try {
      p.load(input);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    return p;
  }

  @Override
  public void store(Properties resource, OutputStream outputStream) {
  }


}
