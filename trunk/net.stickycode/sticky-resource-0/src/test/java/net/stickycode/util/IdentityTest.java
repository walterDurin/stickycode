package net.stickycode.util;

import static org.assertj.core.api.Assertions.assertThat;

import net.stickycode.util.Identity;

import org.junit.Test;

public class IdentityTest {

  public class Anonymous
      implements Identity<Anonymous> {

    private final Integer value;

    public Anonymous(Integer value) {
      this.value = value;
    }

    @Override
    public int compareTo(Anonymous o) {
      return value.compareTo(o.value);
    }

    @Override
    public boolean eq(Anonymous t) {
      if (t == null)
        return false;

      return value.equals(t.value);
    }
  }

  public class AnonymousSubtype
      extends Anonymous {

    public AnonymousSubtype(Integer value) {
      super(value);
    }
  }

  @Test
  public void same() {
//    assertThat(new Anonymous(1)).isEqualTo(new Anonymous(1));
//    assertThat(new Anonymous(1)).isEqualTo(new AnonymousSubtype(1));
  }

  @Test//(expected = ClassCastException.class)
  public void object() {
//    assertThat(new Anonymous(1)).isEqualTo(new Object());
  }

}
