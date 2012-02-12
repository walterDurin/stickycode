package net.stickycode.coercion.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.Resource;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.resource.SingletonResource;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class ResourceCoercion
    extends AbstractNoDefaultCoercion<Resource<Object>> {

  @Inject
  ResourceProtocolRegistry protocols;

  @Inject
  ResourceCodecRegistry codecs;

  @Override
  public Resource<Object> coerce(CoercionTarget type, String value) {
    URI uri = uri(value);
    ResourceProtocol protocol = protocols.find(uri.getScheme());
    ResourceLocation location = new UriResourceLocation(type.getComponentCoercionTypes()[0], protocol, uri);
    ResourceCodec<?> codec = codecs.find(type);
    return new SingletonResource<Object>(codec, location);
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
    return type.getType().isAssignableFrom(Resource.class);
  }

}