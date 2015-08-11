package net.stickycode.configured.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import javax.inject.Inject;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.configured.ConfiguredStrategy;

import org.junit.Test;

public abstract class AbstractConfiguredStrategyTest {

  public static interface Strategy {
  
    String algorithm();
  }

  protected abstract void configure(WithStrategy instance);
  protected abstract void configure(ConfiguredStrategyCoercion instance);

  public static class YesStrategy
      implements Strategy {
  
    @Override
    public String algorithm() {
      return "yes";
    }
  }

  public static class NoStrategy
      implements Strategy {
  
    @Override
    public String algorithm() {
      return "no";
    }
  }

  public static class WithStrategy {
    
    @ConfiguredStrategy
    Strategy strategy;
  }

  @Inject
  protected ConfigurationSystem system;

  public AbstractConfiguredStrategyTest() {
    super();
  }

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
    assertThat(instance.strategy).isNotNull();
    assertThat(instance.strategy.algorithm()).isEqualTo("yes");
  }
  
  @Test
  public void no() {
    WithStrategy instance = new WithStrategy();
    System.setProperty("withStrategy.strategy", "noStrategy");
    configure(instance);
    assertThat(instance.strategy).isNotNull();
    assertThat(instance.strategy.algorithm()).isEqualTo("no");
  }

}
