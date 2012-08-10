package net.stickycode.mockwire.testng;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Mockwire.class)
@MockwireConfigured
public class TestngTest {

  @UnderTest
  Testable testable;
  
  @Test
  public void hi() {
    assertThat(testable).isNotNull();
  }

}
