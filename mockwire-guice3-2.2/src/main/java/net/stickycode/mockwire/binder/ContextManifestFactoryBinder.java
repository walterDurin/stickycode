package net.stickycode.mockwire.binder;

import net.stickycode.mockwire.IsolatedTestManifest;
import net.stickycode.mockwire.guice3.GuiceIsolatedTestManifest;

public class ContextManifestFactoryBinder
    implements ContextManifestFactory {

  @Override
  public IsolatedTestManifest create() {
    return new GuiceIsolatedTestManifest();
  }

}
