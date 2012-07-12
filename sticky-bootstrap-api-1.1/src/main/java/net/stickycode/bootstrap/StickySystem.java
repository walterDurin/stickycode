package net.stickycode.bootstrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class StickySystem {

  private List<StickySubsystem> subsystems;
  
  @Inject
  public StickySystem(Set<StickySubsystem> subsystems) {
    this.subsystems = new ArrayList<StickySubsystem>(subsystems);
    Collections.sort(this.subsystems, new SubsystemDependencyComparator());
  }
  
  public void start() {
    for (StickySubsystem subsystem : subsystems) {
      subsystem.start();
    }
  }
  
  public void shutdown() {
    for (StickySubsystem subsystem : subsystems) {
      subsystem.shutdown();
    }
  }
  
}
