package net.stickycode.bootstrap.tck.component;

import static org.assertj.core.api.StrictAssertions.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.ComponentContainer;
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

  @Inject
  DomainBean domainBean;

  @Inject
  DomainBean domainBean2;

  @Inject
  ComponentContainer container;

  @Test
  public void implementationAndContractAreTheSameInstance() {
    assertThat(implementation).isSameAs(contract);
    assertThat(implementation).isSameAs(contractTwo);
  }

  @Test
  public void componentsScannedAreInjected() {
    assertThat(component).isSameAs(component2);
    assertThat(domainBean).isNotSameAs(domainBean2);
  }

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass());
  }
}
