package net.stickycode.resource;

import java.util.Set;

import javax.inject.Inject;

import net.stickycode.resource.ResourceProtocolNotResolvableException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class StickyResourceProtocolRegistry
    implements ResourceProtocolRegistry {
  
  @Inject
  private Set<ResourceProtocol> protocols;

  @Override
  public ResourceProtocol find(String protocol) {
    if (protocol == null)
      throw new NullPointerException("Null protocols names makes not sense");
    
    for (ResourceProtocol p : protocols) {
      if (p.canResolve(protocol))
        return p;
    }
    
    throw new ResourceProtocolNotResolvableException(protocol, protocols);
  }

}
