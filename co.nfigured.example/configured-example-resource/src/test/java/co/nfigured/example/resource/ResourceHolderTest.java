package co.nfigured.example.resource;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireConfigured({})
@MockwireContainment
public class ResourceHolderTest {

  @Inject
  ResourceHolder holder;

  @Test
  public void load() {
    assertThat(holder.brief.getDescription()).isNull();
    assertThat(holder.funny.getDescription()).isEqualTo("You were born an original. Don't die as a copy.");
    assertThat(holder.verbose.getDescription()).isEqualTo("This is a longer message with a \n new line");
  }

}
