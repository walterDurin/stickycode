package net.stickycode.configured.finder;

public interface BeanFinder {

  /**
   * Return an instance of T
   */
  <T> T find(Class<T> type)
    throws BeanNotFoundException;

}
