package net.stickycode.bootstrap.tck.component;

import static org.assertj.core.api.StrictAssertions.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.StickyBootstrap;

public class ComponentTest {

  @Inject
  StandardComponent component;

  @Inject
  StandardComponent component2;

  @Inject
  TheSubInterface implementation;

  @Inject
  SubInterface contract;

  @Inject
  SubInterface contractTwo;

  @Inject
  UpTheTop upTop;

  @Test
  public void implementationAndContractAreTheSameInstance() {
    assertThat(implementation).isSameAs(contract);
    assertThat(implementation).isSameAs(contractTwo);
  }

  @Test
  public void componentsScannedAreInjected() {
    assertThat(component).isSameAs(component2);
  }

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass());
  }
}
