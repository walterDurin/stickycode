package net.stickycode.resource.codec;

import java.io.IOException;
import java.util.Properties;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceConnection;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class PropertiesResourceCodec
    implements ResourceCodec<Properties> {

  @Override
  public Properties load(ResourceConnection input, CoercionTarget targetType) {
    try {
      Properties p = new Properties();
      p.load(input.getInputStream());
      return p;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void store(CoercionTarget sourceType, Properties resource, ResourceConnection connection) {
    try {
      resource.store(connection.getOutputStream(), getClass().getName());
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
