package net.stickycode.coercion.resource;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.Resource;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.resource.ResourceSpecification;
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
    ResourceSpecification specification = new ResourceSpecification(type.getOwner(), value);
    ResourceProtocol protocol = protocols.find(specification.getProtocol());
    ResourceCodec<?> codec = codecs.find(type.getComponentCoercionTypes()[0]);
    return new SingletonResource<Object>(codec, protocol, specification);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(Resource.class);
  }

}
