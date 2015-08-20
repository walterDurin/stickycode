package net.example.elsewhere;

import javax.inject.Inject;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class OtherBean {

  @Inject
  ComponentContainer container;

}
