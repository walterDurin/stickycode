package net.stickycode.configuration;

import net.stickycode.coercion.CoercionTarget;

public interface ConfigurationTarget
    extends ConfigurationKey {

  void resolvedWith(ResolvedConfiguration resolved);

  CoercionTarget getCoercionTarget();

}
