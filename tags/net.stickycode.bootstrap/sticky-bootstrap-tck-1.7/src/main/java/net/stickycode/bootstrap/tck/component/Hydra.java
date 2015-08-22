package net.stickycode.bootstrap.tck.component;

import javax.inject.Inject;

import net.stickycode.bootstrap.ComponentContainer;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class Hydra
    implements OneUp {

  @Inject
  ComponentContainer injector;

}

interface OneUp
    extends TwoUp, ThreeUp {

}

interface TwoUp
    extends FourUp {

}

interface ThreeUp
    extends FiveUp {

}

interface FourUp
    extends SixUp {

}

interface FiveUp {

}

interface SixUp {

}
