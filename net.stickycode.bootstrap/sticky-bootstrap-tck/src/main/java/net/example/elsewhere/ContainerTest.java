package net.example.elsewhere;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.bootstrap.StickyBootstrap;

public class ContainerTest {

  @Inject
  ComponentContainer container;

  @Before
  public void setup() {
    StickyBootstrap.crank(this, getClass());
  }

  @Test
  public void container() {
    container.find(ComponentContainer.class);
  }

  @Test
  public void childInjector() {
    container.find(OtherBean.class);
  }
}
