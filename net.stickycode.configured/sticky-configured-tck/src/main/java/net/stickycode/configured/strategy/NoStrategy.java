package net.stickycode.configured.strategy;

import net.stickycode.configured.strategy.ConfiguredStrategyTest.Strategy;
import net.stickycode.stereotype.plugin.StickyStrategy;

@StickyStrategy
public class NoStrategy
    implements Strategy {

  @Override
  public String algorithm() {
    return "no";
  }
}