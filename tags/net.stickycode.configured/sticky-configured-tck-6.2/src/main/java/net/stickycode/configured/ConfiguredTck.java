package net.stickycode.configured;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.stickycode.configured.finder.BeanFinderTest;
import net.stickycode.configured.strategy.ConfiguredStrategyTest;

@SuiteClasses({ConfiguredComponentTest.class,
  JustPostConfiguredTest.class,
  PrimitiveConfiguratedTest.class,
  BeanFinderTest.class,
  ConfiguredStrategyTest.class})
@RunWith(Suite.class)
public class ConfiguredTck {

}
