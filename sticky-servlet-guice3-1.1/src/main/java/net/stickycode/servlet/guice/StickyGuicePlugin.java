package net.stickycode.servlet.guice;

import java.util.List;

import com.google.inject.Binder;


public interface StickyGuicePlugin {

  List<ServletMapping> getServletMappings();

  void bind(Binder binder);


}
