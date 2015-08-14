package net.stickycode.bootstrap.tck.plugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.StickyBootstrap;

public class PluggableTest {

  @Inject
  Set<Pluggable> plugged;

  @Inject
  Set<PluggableContract> abstractInTheMiddle;

  @Inject
  Set<GenericPluggable<?>> genericPlugged;

  @SuppressWarnings("rawtypes")
  @Inject
  Set<GenericPluggable> genericPluggedNoWildcard;

  @Test
  public void verify() {
    assertThat(plugged).hasSize(2);
    assertThat(genericPluggedNoWildcard).hasSize(3);
    assertThat(genericPlugged).hasSize(3);
  }

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass());
  }
}
