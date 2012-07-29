package net.stickycode.servlet.guice3.jersey;

import java.util.Arrays;
import java.util.List;

import net.stickycode.servlet.guice.ServletMapping;
import net.stickycode.servlet.guice.StickyGuicePlugin;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.plugin.StickyExtension;

import com.google.inject.Binder;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

@StickyExtension
public class JerseyStickyGuicePlugin
    implements StickyGuicePlugin {

  @Configured
  private String servicesPath = "/rs/*";

  @Override
  public void bind(Binder binder) {
    binder.install(new JerseyServletModule());
    binder.bind(GuiceContainer.class);
  }

  @Override
  public List<ServletMapping> getServletMappings() {
    return Arrays.asList(new ServletMapping(servicesPath, GuiceContainer.class));
  }
}
