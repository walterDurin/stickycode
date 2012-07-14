package net.stickycode.bootstrap;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class StickyBootstrapTest {

  private StickySystem base = new SystemStub("base");

  private StickySystem usesBase = new SystemStub("usesBase").dependsOn(base);

  private StickySystem usedByBase = new SystemStub("usedByBase").isDependedOnBy(base);

  private StickySystem beforeAll = new SystemStub("beforeAll") {

    public boolean isUsedBy(StickySystem system) {
      return true;
    }
  };

  private StickySystem beforeAll2 = new SystemStub("beforeAll2") {

    public boolean isUsedBy(StickySystem system) {
      return true;
    }
  };

  @Test
  public void same() {
    assertThat(ordered(base)).containsExactly(base);
  }

  @Test
  public void uses() {
    assertThat(ordered(base, usesBase)).containsExactly(base, usesBase);
    assertThat(ordered(usesBase, base)).containsExactly(base, usesBase);
  }

  @Test
  public void usedBy() {
    assertThat(ordered(usedByBase, base)).containsExactly(usedByBase, base);
    assertThat(ordered(base, usedByBase)).containsExactly(usedByBase, base);
  }

  @Test
  public void usedByAndUses() {
    assertThat(ordered(usesBase, base, usedByBase)).containsExactly(usedByBase, base, usesBase);
    assertThat(ordered(base, usesBase, usedByBase)).containsExactly(usedByBase, base, usesBase);
    assertThat(ordered(usedByBase, usesBase, base)).containsExactly(usedByBase, base, usesBase);
  }

  @Test
  public void cycle() {
    SystemStub cycle = new SystemStub("cycle");
    StickySystem cycle2 = new SystemStub("cycle2").dependsOn(cycle);
    cycle.dependsOn(cycle2);
    assertThat(ordered(cycle, cycle2)).containsExactly(cycle, cycle2);
    assertThat(ordered(cycle2, cycle)).containsExactly(cycle2, cycle);
    assertThat(ordered(cycle2, base, cycle)).containsExactly(cycle2, base, cycle);
  }

  @Test
  public void before() {
    assertThat(ordered(beforeAll, beforeAll2))
        .containsExactly(beforeAll, beforeAll2);
  }

  @Test
  public void beforeAll() {
    assertThat(ordered(base, usedByBase, usesBase, beforeAll))
        .containsExactly(beforeAll, usedByBase, base, usesBase);
  }

  @Test
  public void beforeAllx2() {
    assertThat(ordered(base, usedByBase, beforeAll2, usesBase, beforeAll))
        .containsExactly(beforeAll2, beforeAll, usedByBase, base, usesBase);
  }

  @Test
  public void shutdownBeforeAllx2() {
    assertThat(shutdownOrder(base, usedByBase, beforeAll2, usesBase, beforeAll))
        .containsExactly(usesBase, base, usedByBase, beforeAll, beforeAll2);
  }

  private List<StickySystem> ordered(StickySystem... base2) {
    StickyBootstrap bootstrap = new StickyBootstrap(new ArraySet<StickySystem>(base2));
    return bootstrap.startOrder();
  }

  private List<StickySystem> shutdownOrder(StickySystem... base2) {
    StickyBootstrap bootstrap = new StickyBootstrap(new ArraySet<StickySystem>(base2));
    return bootstrap.shutdownOrder();
  }
}
