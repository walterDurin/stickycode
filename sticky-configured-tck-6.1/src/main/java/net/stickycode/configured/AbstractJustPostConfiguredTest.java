package net.stickycode.configured;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.bootstrap.StickyBootstrap;

import org.junit.Test;

public abstract class AbstractJustPostConfiguredTest {

  @Inject
  private StickyBootstrap bootstrap;

  public AbstractJustPostConfiguredTest() {
    super();
  }

  @Test
  public void isRegistered() {
    JustPostConfigured target = new JustPostConfigured();
    configured(target);
    bootstrap.start();
    assertThat(target.isPostConfigured()).isTrue();
  }

  abstract protected void configured(Object target);

}
