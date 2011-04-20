package net.stickycode.mockwire.binder;

import net.stickycode.mockwire.IsolatedTestManifest;

/**
 * Factory to create IsolatedTestManifest
 */
public interface ContextManifestFactory {

  IsolatedTestManifest create();

}
