package net.stickycode.resource.protocol;

import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class FileResourceProtocol
    implements ResourceProtocol {

  @Override
  public boolean canResolve(String protocol) {
    return protocol.startsWith("file");
  }

  @Override
  public ResourceInput createInput(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return new FileResourceInput(resourceLocation);
  }

  @Override
  public ResourceOutput createOutput(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return new FileResourceOutput(resourceLocation);
  }
}
