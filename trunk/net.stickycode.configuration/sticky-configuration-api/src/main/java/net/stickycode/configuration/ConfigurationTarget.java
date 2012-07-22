package net.stickycode.configuration;

public interface ConfigurationTarget
    extends ConfigurationKey {

  void resolvedWith(ResolvedConfiguration resolved);

  ResolvedConfiguration getResolution();

}
