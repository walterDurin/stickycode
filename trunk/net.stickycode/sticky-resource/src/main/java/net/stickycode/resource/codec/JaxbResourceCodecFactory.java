package net.stickycode.resource.codec;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceCodecFactory;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class JaxbResourceCodecFactory
    implements ResourceCodecFactory<Object> {

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    if (type.getType().isAssignableFrom(JAXBElement.class))
      return true;

    return type.getType().isAnnotationPresent(XmlRootElement.class);
  }

  @Override
  public ResourceCodec<Object> create(CoercionTarget type) {
    if (type.getType().isAnnotationPresent(XmlRootElement.class))
      return new JaxbResourceCodec(type.getType());

    return new JaxbElementResourceCodec(type.getComponentCoercionTypes()[0].getType());
  }

}
