package net.stickycode.resource;

import java.util.Set;

import javax.inject.Inject;

import net.stickycode.resource.ProtocolNotResolvableException;
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
    for (ResourceProtocol p : protocols) {
      if (p.canResolve(protocol))
        return p;
    }
    
    throw new ProtocolNotResolvableException(protocol, protocols);
  }

}