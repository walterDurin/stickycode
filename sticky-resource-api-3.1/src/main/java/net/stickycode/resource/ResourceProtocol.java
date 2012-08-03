package net.stickycode.resource;

/**
 * A {@link ResourceProtocol} represents a wire protocol for a resource, it can creation {@link ResourceConnection}s to
 * resources.
 */
public interface ResourceProtocol {

  <T> ResourceConnection<T> createConnection(ResourceLocation resourceLocation)
      throws ResourceNotFoundException;

  boolean canResolve(String protocol);

}
