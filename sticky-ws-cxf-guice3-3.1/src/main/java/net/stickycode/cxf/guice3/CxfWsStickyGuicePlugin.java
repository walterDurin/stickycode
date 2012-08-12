package net.stickycode.cxf.guice3;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import net.stickycode.servlet.guice.ServletMapping;
import net.stickycode.servlet.guice.StickyGuicePlugin;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;

import com.google.inject.Binder;
import com.google.inject.Provider;

@StickyExtension
public class CxfWsStickyGuicePlugin
    implements StickyGuicePlugin {

  @Configured
  private String servicesPath = "/services/*";

  @Override
  public void bind(Binder binder) {
    binder.bind(StickyCxfServlet.class).in(Singleton.class);
    binder.bind(Bus.class).toProvider(new Provider<Bus>() {

      @Override
      public Bus get() {
        return BusFactory.newInstance().createBus();
      }
    }).in(Singleton.class);
  }

  @Override
  public List<ServletMapping> getServletMappings() {
    return Arrays.asList(new ServletMapping(servicesPath, StickyCxfServlet.class));
  }
}
