package net.stickycode.mockwire.guice3.provider;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment("net.stickycode.mockwire.guice3.provider")
public class ProviderTest {

  @Inject
  ProviderContainer container;
  
  @Test
  public void provider() {
    assertThat(container).isNotNull();
    assertThat(container.stuff()).isNotNull();
    assertThat(container.stuff()).isNotSameAs(container.stuff());
  }
  
}
