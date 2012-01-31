package net.stickycode.resource;

import static org.fest.assertions.Assertions.assertThat;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;

import org.junit.Test;

public class ResourceSpecificationTest implements Resource<String>{

  @Test(expected = NullPointerException.class)
  public void nullContextCheck() {
    new ResourceSpecification(null, "A");
  }

  @Test(expected = NullPointerException.class)
  public void nullCheck() {
    new ResourceSpecification(getTarget(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankCheck() {
    new ResourceSpecification(getTarget(), "");
  }

  @Test
  public void classpath() {
    assertThat(new ResourceSpecification(getTarget(), "a").getProtocol()).isEqualTo("classpath");
    assertThat(new ResourceSpecification(getTarget(), "a").getPath()).isEqualTo("a");
    assertThat(new ResourceSpecification(getTarget(), "::a").getPath()).isEqualTo("a");
    assertThat(new ResourceSpecification(getTarget(), "classpath://a").getProtocol()).isEqualTo("classpath");
    assertThat(new ResourceSpecification(getTarget(), "classpath://a").getPath()).isEqualTo("a");
    assertThat(new ResourceSpecification(getTarget(), "::classpath://a").getPath()).isEqualTo("a");
  }

  @Test
  public void dummy() {
    assertThat(new ResourceSpecification(getTarget(), "dummy://a").getProtocol()).isEqualTo("dummy");
    assertThat(new ResourceSpecification(getTarget(), "::dummy://a").getProtocol()).isEqualTo("dummy");
  }

  private CoercionTarget getTarget() {
    return CoercionTargets.find(getClass());
  }

  @Override
  public String get() {
    return "huh?";
  }

}
