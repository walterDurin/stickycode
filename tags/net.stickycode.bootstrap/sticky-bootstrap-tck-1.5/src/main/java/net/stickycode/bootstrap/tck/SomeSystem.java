package net.stickycode.bootstrap.tck;

import net.stickycode.bootstrap.AbstractStickySystem;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class SomeSystem
    extends AbstractStickySystem {

  @Override
  public String getLabel() {
    return "SomeSystem";
  }

}
