package net.stickycode.resource;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.resource.codec.JaxbResourceCodec;
import net.stickycode.resource.codec.PropertiesResourceCodec;
import net.stickycode.resource.codec.StringResourceCodec;
import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class StickyResourceCodecRegistry
    implements ResourceCodecRegistry {

  @Override
  public ResourceCodec<?> find(Class<?> type) {
    if (type.isAssignableFrom(String.class))
      return new StringResourceCodec();

    if (type.isAnnotationPresent(XmlRootElement.class))
      return new JaxbResourceCodec(type);
    
    if (type.isAssignableFrom(Properties.class))
      return new PropertiesResourceCodec();
    
    throw new RuntimeException("No codec for type " + type);
  }

}
