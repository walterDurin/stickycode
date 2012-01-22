package net.stickycode.resource;

import java.util.Set;

import javax.inject.Inject;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class StickyResourceCodecRegistry
    implements ResourceCodecRegistry {

  @SuppressWarnings("rawtypes")
  @Inject
  private Set<ResourceCodecFactory> codecFactories;

  @SuppressWarnings("rawtypes")
  @Inject
  private Set<ResourceCodec> codecs;

  @Override
  public ResourceCodec<?> find(CoercionTarget type) {
    for (ResourceCodecFactory<?> r : codecFactories) {
      if (r.isApplicableTo(type))
        return r.create(type);
    }
    for (ResourceCodec<?> r : codecs) {
      if (r.isApplicableTo(type))
        return r;
    }

    throw new RuntimeException("No codec for type " + type);
  }

}
