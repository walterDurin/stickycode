package net.stickycode.resource;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class StickyResourceCodecRegistry
    implements ResourceCodecRegistry {

  private Logger log = LoggerFactory.getLogger(getClass());

  @SuppressWarnings("rawtypes")
  @Inject
  private Set<ResourceCodec> codecs;

  @Override
  public <T> ResourceCodec<T> find(CoercionTarget target) {
    CoercionTarget componentTarget = target.getComponentCoercionTypes()[0];
    log.info("find a codec for {}", componentTarget);
    ResourceCodec<T> lookup = lookup(target, componentTarget);
    log.info("found {}", lookup);
    return lookup;
  }

  @SuppressWarnings("unchecked")
  private <T> ResourceCodec<T> lookup(CoercionTarget target, CoercionTarget componentTarget) {
    for (ResourceCodec<?> r : codecs) {
      if (r.isApplicableTo(componentTarget))
        return (ResourceCodec<T>) r;
    }

    throw new ResourceCodecNotFoundException(target, codecs);
  }

}
