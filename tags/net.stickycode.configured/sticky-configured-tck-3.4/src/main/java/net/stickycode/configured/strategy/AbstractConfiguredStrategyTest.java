package net.stickycode.configured.strategy;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.configured.ConfiguredField;
import net.stickycode.configured.strategy.ConfiguredStrategyCoercion;
import net.stickycode.stereotype.ConfiguredStrategy;

import org.junit.Test;

public abstract class AbstractConfiguredStrategyTest {

  public static interface Strategy {
  
    String algorithm();
  }

  protected abstract void configure(WithStrategy instance);

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
    ConfiguredField target = new ConfiguredField(new WithStrategy(), field);
    assertThat(target.hasAnnotation(ConfiguredStrategy.class));
    assertThat(new ConfiguredStrategyCoercion().isApplicableTo(target)).isTrue();
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
