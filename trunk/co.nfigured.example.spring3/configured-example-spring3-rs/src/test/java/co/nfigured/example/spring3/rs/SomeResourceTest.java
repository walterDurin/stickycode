package co.nfigured.example.spring3.rs;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
public class SomeResourceTest {

  @UnderTest
  SomeResource resource;

  @Test
  public void name() {
    assertThat(resource.getName().getValue()).isEqualTo("My name is Slim Shady!");
  }

}
