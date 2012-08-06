package net.stickycode.resource.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class PropertiesResourceCodec
    implements ResourceCodec<Properties> {

  @Override
  public Properties load(CoercionTarget resourceTarget, InputStream input, Charset characterSet) {
    try {
      Properties p = new Properties();
      p.load(input);
      return p;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void store(CoercionTarget sourceType, Properties resource, OutputStream output, Charset characterSet) {
    try {
      resource.store(output, getClass().getName());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(Properties.class);
  }

  @Override
  public String getDefaultFileSuffix() {
    return ".properties";
  }

}
