package net.stickycode.bootstrap.tck.provider;

import static org.assertj.core.api.StrictAssertions.assertThat;

import javax.inject.Inject;
import javax.inject.Provider;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.StickyBootstrap;

public class ProviderTest {

  @Inject
  Provider<SomethingProvider> componentProvider;

  @Test
  public void ensureStandardComponentProvidersReturnSingletonsAsExpected() {
    assertThat(componentProvider).isNotNull();
    assertThat(componentProvider.get()).isSameAs(componentProvider.get());
  }

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass());
  }
}
