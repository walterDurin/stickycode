package net.stickycode.configured.strategy;

import java.util.Set;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class StrategyNotFoundException
    extends PermanentException {

  public StrategyNotFoundException(Class<?> contract, String name, Set<String> set) {
    super("Could not find an implementation of {} called {}. The possible names are {}",
        contract.getName(), name, set);
  }
}
