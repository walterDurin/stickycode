package net.stickycode.bootstrap.tck.provider;

import static org.assertj.core.api.StrictAssertions.assertThat;

import javax.inject.Inject;
import javax.inject.Provider;

import org.junit.Test;

import net.stickycode.bootstrap.tck.component.StandardComponent;

public abstract class AbstractProviderTest {

  @Inject
  Provider<StandardComponent> componentProvider;

  @Test
  public void ensureStandardComponentProvidersReturnSingletonsAsExpected() {
    assertThat(componentProvider).isNotNull();
    assertThat(componentProvider.get()).isSameAs(componentProvider.get());
  }

}
