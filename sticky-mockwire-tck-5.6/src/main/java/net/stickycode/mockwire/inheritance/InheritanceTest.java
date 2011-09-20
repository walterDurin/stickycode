package net.stickycode.mockwire.inheritance;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
public class InheritanceTest {

  public static interface SuperSuper {
  }

  public static interface Super
      extends SuperSuper {
  }

  public static class ParentParent {
  }

  public static interface SuperParent {
  }

  public static class Parent
      extends ParentParent
      implements SuperParent {
  }

  public static class Concrete
      extends Parent
      implements Super {
  }

  @UnderTest
  Concrete concrete;

  @Inject
  SuperSuper superSuper;

  @Inject
  Super souper;

  @Inject
  SuperParent superParent;

  @Inject
  ParentParent parentParent;

  @Inject
  Parent parent;

  @Test
  public void inheritededInjection() {
    assertThat(superSuper).isNotNull();
    assertThat(souper).isNotNull();
    assertThat(superParent).isNotNull();
    assertThat(parentParent).isNotNull();
    assertThat(parent).isNotNull();
  }
}
