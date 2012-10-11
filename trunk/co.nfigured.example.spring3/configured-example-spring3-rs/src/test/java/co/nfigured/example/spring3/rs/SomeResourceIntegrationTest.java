package co.nfigured.example.spring3.rs;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.configured.Configured;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireConfigured("resource=http://localhost:3345/someResource")
public class SomeResourceIntegrationTest {

  @Configured
  Name resource;
  
  @Test
  public void name() {
    assertThat(resource.getValue()).isEqualTo("My name is Slim Shady!");
  }
}
