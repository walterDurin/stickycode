package net.stickycode.resource.protocol;

import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class ClasspathResourceProtocol
    implements ResourceProtocol {

  @Override
  public boolean canResolve(String protocol) {
    return "classpath".equals(protocol);
  }

  @Override
  public ResourceInput createInput(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    return new ClasspathResourceInput(resourceLocation);
  }

  @Override
  public ResourceOutput createOutput(ResourceLocation resourceLocation) throws ResourceNotFoundException {
    throw new UnsupportedOperationException();
  }

}
