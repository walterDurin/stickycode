package net.stickycode.util;

import static org.assertj.core.api.Assertions.assertThat;

import net.stickycode.util.MultiLinked;

import org.junit.Test;

public class MultiLinkedTest {

  @Test
  public void empty() {
    assertThat(multiLinked().iterate("a")).isEmpty();
  }

  @Test(expected = NullPointerException.class)
  public void nullKey() {
    multiLinked().iterate(null);
  }

  @Test
  public void one() {
    MultiLinked<String, AnonymousLinked> multiLinked = multiLinked();
    AnonymousLinked element = new AnonymousLinked();
    multiLinked.put("a", element);
    assertThat(multiLinked.iterate("b")).isEmpty();
    assertThat(multiLinked.iterate("a")).containsOnly(element);
  }

  @Test
  public void two() {
    MultiLinked<String, AnonymousLinked> multiLinked = multiLinked();
    AnonymousLinked element = new AnonymousLinked();
    AnonymousLinked e2 = new AnonymousLinked();
    element.setNext(e2);
    multiLinked.put("a", element);
    assertThat(multiLinked.iterate("b")).isEmpty();
    assertThat(multiLinked.iterate("a")).containsOnly(element, e2);
  }

  private MultiLinked<String, AnonymousLinked> multiLinked() {
    return new MultiLinked<String, AnonymousLinked>();
  }

}
