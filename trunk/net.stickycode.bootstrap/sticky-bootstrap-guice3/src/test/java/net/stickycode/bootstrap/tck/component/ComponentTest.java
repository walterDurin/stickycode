package net.stickycode.bootstrap.tck.component;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import net.stickycode.bootstrap.tck.AbstractBootstrapTest;

public class ComponentTest
    extends AbstractBootstrapTest {

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

  @Test
  public void implementationAndContractAreTheSameInstance() {
    assertThat(implementation).isSameAs(contract);
    assertThat(implementation).isSameAs(contractTwo);
  }

  @Test
  public void componentsScannedAreInjected() {
    assertThat(component).isSameAs(component2);
  }
}
