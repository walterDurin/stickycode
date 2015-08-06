package net.stickycode.configuration;

import net.stickycode.coercion.CoercionTarget;

/**
 * A target that can be configured.
 */
public interface ConfigurationTarget
    extends ConfigurationKey {

  /**
   * Add a resolution path
   */
  void resolvedWith(ResolvedConfiguration resolved);

  /**
   * @return the type information for the target of configuration.
   */
  CoercionTarget getCoercionTarget();

}
