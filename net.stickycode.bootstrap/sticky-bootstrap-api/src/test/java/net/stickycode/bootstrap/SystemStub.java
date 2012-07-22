package net.stickycode.bootstrap;

import java.util.ArrayList;
import java.util.Collection;

class SystemStub
    extends AbstractStickySystem {

  private String label;

  private Collection<StickySystem> isUsedBy = new ArrayList<StickySystem>();

  private Collection<StickySystem> uses = new ArrayList<StickySystem>();

  public SystemStub(String name) {
    super();
    this.label = name;
  }

  public SystemStub dependsOn(StickySystem system) {
    uses.add(system);
    return this;
  }

  public SystemStub isDependedOnBy(StickySystem system) {
    isUsedBy.add(system);
    return this;
  }

  @Override
  public boolean uses(StickySystem system) {
    return uses.contains(system);
  }

  @Override
  public boolean isUsedBy(StickySystem system) {
    return isUsedBy.contains(system);
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public Package getPackage() {
    return getClass().getPackage();
  }

}
