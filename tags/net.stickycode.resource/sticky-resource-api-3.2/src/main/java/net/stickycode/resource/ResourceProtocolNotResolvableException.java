package net.stickycode.resource;

import java.util.Set;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ResourceProtocolNotResolvableException
    extends PermanentException {

  public ResourceProtocolNotResolvableException(String protocol, Set<ResourceProtocol> protocols) {
    super("Failed to find a protocol handler for {} in {}", protocol, protocols);
  }

}
