package net.stickycode.mockwire.guice3;

import java.util.List;

import net.stickycode.configuration.ConfigurationSource;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ConfigurationSourceModule
    extends AbstractModule {

  private final List<ConfigurationSource> configurationSources;

  public ConfigurationSourceModule(List<ConfigurationSource> configurationSource) {
    this.configurationSources = configurationSource;
  }

  @Override
  protected void configure() {
    if (configurationSources == null)
      return;

    Multibinder<ConfigurationSource> sources = Multibinder.newSetBinder(binder(), ConfigurationSource.class);
    for (ConfigurationSource source : configurationSources) {
      sources.addBinding().toInstance(source);
    }
  }
}
