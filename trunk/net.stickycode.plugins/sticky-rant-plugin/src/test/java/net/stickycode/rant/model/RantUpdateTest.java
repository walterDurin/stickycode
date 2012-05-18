package net.stickycode.rant.model;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.stereotype.Resource;
import net.stickycode.stereotype.Configured;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(MockwireRunner.class)
@MockwireConfigured({"rantUpdateTest.rants.uri=file://src/test/resources/${testName}.xml"})
public class RantUpdateTest {

  @Configured
  Resource<Rants> rants;
  
  @Test
  public void identity() {
    Rants value = rants.get();
    rants.set(value);
    assertThat(value == rants.get()).isFalse();
    
    assertThat(value.equals(rants.get()));
  }
}
