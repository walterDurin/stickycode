package net.stickycode.util;

import static org.assertj.core.api.Assertions.assertThat;

import net.stickycode.util.LinkedIterator;

import org.junit.Test;

public class LinkedIteratorTest {

  @Test
  public void empty() {
    assertThat(new LinkedIterator<AnonymousLinked>(null)).isEmpty();
  }

  @Test
  public void one() {
    AnonymousLinked linked = new AnonymousLinked();
    assertThat(new LinkedIterator<AnonymousLinked>(linked)).containsOnly(linked);
  }

  @Test
  public void two() {
    AnonymousLinked linked = new AnonymousLinked();
    AnonymousLinked l2 = new AnonymousLinked();
    linked.setNext(l2);
    assertThat(new LinkedIterator<AnonymousLinked>(linked)).containsOnly(linked, l2);
  }
}
