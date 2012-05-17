package net.stickycode.coercion.resource;

import java.net.URI;

import java.net.URISyntaxException;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceCodecNotFoundException;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.resource.SingletonResource;
import net.stickycode.stereotype.component.StickyExtension;


@StickyExtension
public class ResourceCoercion
    extends AbstractNoDefaultCoercion<Object> {

  @Inject
  ResourceProtocolRegistry protocols;

  @Inject
  ResourceCodecRegistry codecs;

  @Override
  public Object coerce(CoercionTarget type, String value) {
    URI uri = uri(value);
    ResourceProtocol protocol = protocols.find(uri.getScheme());
    ResourceLocation location = new UriResourceLocation(type, protocol, uri);
    ResourceCodec<?> codec = codecs.find(type);
    return new SingletonResource<Object>(codec, location).get();
  }

  private URI uri(String value) {
    try {
      if (value.contains("://"))
        return new URI(value);
      else
        return new URI("classpath://" + value);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    try {
      codecs.find(type);
      return true;
    }
    catch (ResourceCodecNotFoundException e) {
      return false;
    }
  }
  
  @Override
  public Object getDefaultValue(CoercionTarget type) {
    ResourceCodec<?> codec = codecs.find(type);
    URI uri = uri(type.getName() + codec.getDefaultFileSuffix()); 
    ResourceProtocol protocol = protocols.find(uri.getScheme());
    ResourceLocation location = new UriResourceLocation(type, protocol, uri);
    return new SingletonResource<Object>(codec, location).get();
  }
  
  @Override
  public boolean hasDefaultValue() {
    return true;
  }
}
