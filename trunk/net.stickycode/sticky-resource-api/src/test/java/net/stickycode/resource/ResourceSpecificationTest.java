package net.stickycode.resource;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ResourceSpecificationTest {

  @Test(expected = NullPointerException.class)
  public void nullContextCheck() {
    new ResourceSpecification(null, "A");
  }

  @Test(expected = NullPointerException.class)
  public void nullCheck() {
    new ResourceSpecification(getClass(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void blankCheck() {
    new ResourceSpecification(getClass(), "");
  }

  @Test
  public void classpath() {
    assertThat(new ResourceSpecification(getClass(), "a").getProtocol()).isEqualTo("classpath");
    assertThat(new ResourceSpecification(getClass(), "a").getPath()).isEqualTo("a");
    assertThat(new ResourceSpecification(getClass(), "classpath://a").getProtocol()).isEqualTo("classpath");
    assertThat(new ResourceSpecification(getClass(), "classpath://a").getPath()).isEqualTo("a");
  }

  @Test
  public void dummy() {
    assertThat(new ResourceSpecification(getClass(), "dummy://a").getProtocol()).isEqualTo("dummy");
    assertThat(new ResourceSpecification(getClass(), "::dummy://a").getProtocol()).isEqualTo("dummy");
  }

}
