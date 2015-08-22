package net.stickycode.configured.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.lang.reflect.Field;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.configured.ConfiguredStrategy;

public class ConfiguredStrategyTest {

  public static interface Strategy {

    String algorithm();
  }

  protected void configure(WithStrategy instance) {
    StickyBootstrap.crank(this, getClass()).inject(instance);
    system.start();
  }

  protected void configure(ConfiguredStrategyCoercion instance) {
    StickyBootstrap.crank(this, getClass()).inject(instance);
    system.start();
  }

  public static class WithStrategy {

    @ConfiguredStrategy
    Strategy strategy;
  }

  @Inject
  protected ConfigurationSystem system;

  @Inject
  private Set<Strategy> strategies;

  @Test
  public void applicable() throws SecurityException, NoSuchFieldException {
    Field field = WithStrategy.class.getDeclaredField("strategy");
    ConfiguredStrategyCoercion configuredStrategyCoercion = new ConfiguredStrategyCoercion();
    configure(configuredStrategyCoercion);
    assertThat(configuredStrategyCoercion.isApplicableTo(CoercionTargets.find(field))).isTrue();
  }

  @Test
  public void yes() {
    WithStrategy instance = new WithStrategy();
    System.setProperty("withStrategy.strategy", "yesStrategy");
    configure(instance);
    assertThat(strategies).isNotEmpty();
    assertThat(instance.strategy).isNotNull();
    assertThat(instance.strategy.algorithm()).isEqualTo("yes");
  }

  @Test
  public void no() {
    WithStrategy instance = new WithStrategy();
    System.setProperty("withStrategy.strategy", "noStrategy");
    configure(instance);
    assertThat(strategies).isNotEmpty();
    assertThat(instance.strategy).isNotNull();
    assertThat(instance.strategy.algorithm()).isEqualTo("no");
  }

}
