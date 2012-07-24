package net.stickycode.configured.strategy;

public interface StrategyFinder {

  /**
   * Return the strategy with the given name
   */
  Object findWithName(Class<?> contract, String name)
    throws StrategyNotFoundException;
}
