package net.stickycode.bootstrap;

import java.util.List;

public interface StickySubsystem {

  void start();
  void shutdown();

  List<Class<?>> before();
  List<Class<?>> after();

}
