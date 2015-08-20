package net.stickycode.bootstrap;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface StickyBootstrap {

  static StickyBootstrap crank(Object target, String... packages) {
    StickyBootstrap crank = crank();
    if (packages != null && packages.length > 0)
      return crank.scan(packages).inject(target);

    return crank.inject(target);
  }

  static StickyBootstrap crank(Object target, Class<?> base) {
    return crank().scan(base.getPackage().getName()).inject(target);
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

  StickyBootstrap inject(Object value);

  public <T> T find(Class<T> type);
}
