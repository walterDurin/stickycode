package net.stickycode.configured;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.bootstrap.StickySystemStartup;

import org.junit.Test;

public class JustPostConfiguredTest {

  @Inject
  private StickySystemStartup bootstrap;

  @Test
  public void isRegistered() {
    JustPostConfigured target = new JustPostConfigured();
    configured(target);
    bootstrap.start();
    assertThat(target.isPostConfigured()).isTrue();
  }

  protected void configured(Object target) {
    StickyBootstrap.crank(this, getClass()).inject(target);
  }

}
