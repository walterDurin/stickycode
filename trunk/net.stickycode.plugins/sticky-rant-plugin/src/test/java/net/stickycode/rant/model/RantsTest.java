package net.stickycode.rant.model;


import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.Configured;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireConfigured({"rantsTest.rants=${testName}.xml"})
public class RantsTest {

  @Configured
  private Rants rants;
  
  @Test
  public void empty() {
    assertThat(rants).isEmpty();
  }
  
  @Test
  public void blank() {
    assertThat(rants).isEmpty();
  }
  
  @Test
  public void defect() {
    assertThat(rants).hasSize(1);
    assertThat(rants.getDefects()).hasSize(1);
  }
}
