package net.stickycode.bootstrap;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface StickyBootstrap
    extends ComponentContainer {

  static void crank(Object target, String... packages) {
    crank().scan(packages).inject(target);
  }

  static void crank(Object target, Class<?> base) {
    crank().scan(base.getPackage().getName()).inject(target);
  }

  static StickyBootstrap crank() {
    ServiceLoader<StickyBootstrap> loader = ServiceLoader.load(StickyBootstrap.class);
    Iterator<StickyBootstrap> bootstraps = loader.iterator();
    if (!bootstraps.hasNext())
      throw new RuntimeException("no net.stickycode.bootstrap.StickyBootstrap");

    StickyBootstrap bootstrap = bootstraps.next();
    if (bootstraps.hasNext())
      throw new RuntimeException("too many bootstraps");

    return bootstrap;
  }

  StickyBootstrap scan(String... packages);

}
