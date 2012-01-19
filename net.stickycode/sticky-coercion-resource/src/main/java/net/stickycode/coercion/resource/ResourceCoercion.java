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
    // if protocol is classpath
    // ResourceReference = new ClasspathResourceReference(root, specification.getUri());
    ResourceProtocol protocol = protocols.find(specification.getProtocol());
    // if scheme is ondemand or default
    // return new OndemandResource<String>(reference);
    ResourceCodec<?> codec = codecs.find(type.getComponentCoercionTypes()[0].getType());
    Object object = codec.load(protocol.getInputStream(specification));
    return new SingletonResource<Object>(codec, protocol, specification);
    // ResourceFactory factory = factories.find(type, specification);
    // return factory.create(reference);
    // return new Resource<Object>() {
    //
    // @Override
    // public Object get() {
    // return "hmm";
    // }
    //
    // };
    // ResourceType resourceType = ResourceTypes.find(type.getComponentCoercionTypes()[0]);
    // int firstColon = value.indexOf(':');
    // if (firstColon == -1)
    // return (ResourceLoader<T>) new StringResourceLoader(new ClasspathResource(type.getOwner(), value));
    //
    // return null;//factory.create(value);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(Resource.class);
  }

}
