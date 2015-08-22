package net.stickycode.bootstrap;

public interface ComponentContainer {

  void inject(Object value);

  <T> T find(Class<T> type);

}
