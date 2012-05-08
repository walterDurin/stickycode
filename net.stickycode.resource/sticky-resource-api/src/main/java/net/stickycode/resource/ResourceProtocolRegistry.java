package net.stickycode.resource;

public interface ResourceProtocolRegistry {

  ResourceProtocol find(String protocol)
      throws ResourceProtocolNotResolvableException;

}
