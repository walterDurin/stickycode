package net.stickycode.bootstrap;

import java.util.Comparator;

class SubsystemDependencyComparator
    implements Comparator<StickySubsystem> {

  @Override
  public int compare(StickySubsystem o1, StickySubsystem o2) {
    if (o1.after().contains(o2))
      return 1;
    
    if (o2.after().contains(o1))
      return -1;
    
    if (o1.before().contains(o2))
      return -1;
    
    if (o2.before().contains(o1))
      return 1;
    
    return 0;
  }
}