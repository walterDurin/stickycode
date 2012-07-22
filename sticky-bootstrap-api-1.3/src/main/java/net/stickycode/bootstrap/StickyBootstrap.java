package net.stickycode.bootstrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Bootstrap for the StickyCode systems, which are defined by being beans annotated by {@link StickyPlugin} and implementing
 * {@link StickySystem}.
 */
@StickyComponent
public class StickyBootstrap {

  private Logger log = LoggerFactory.getLogger(getClass());

  private List<StickySystem> systems;

  @Inject
  public StickyBootstrap(Set<StickySystem> subsystems) {
    this.systems = orderByDependency(subsystems);
    log.info("bootstrap order {}", systems);
  }

  private List<StickySystem> orderByDependency(Set<StickySystem> subsystems) {
    LinkedList<StickySystem> order = new LinkedList<StickySystem>(subsystems);
    sort(order);
    return order;
  }

  /**
   * A stable Sort algorithm that
   * <ol>
   * <li>starts at the head
   * <li>
   * <li>finds elements that the head uses
   * <li>
   * <li>bring it to front
   * <li>
   * <li>repeat for the new head</li>
   * <li>if there are no deps then the current head is in the correct place</li>
   * <li>repeat for next element</li>
   * </ol>
   * 
   * A form of selection sort, the sets will always be small so efficiency is not a big deal over the simplicity.
   */
  private void sort(LinkedList<StickySystem> order) {
    if (order.isEmpty())
      return;

    StickySystem head = order.pop();

    StickySystem usedBy = findUses(head, order);
    while (usedBy != head) {
      order.push(head);
      head = usedBy;
      usedBy = findUses(head, order);
    }
    sort(order);

    order.push(head);
  }

  private StickySystem findUses(StickySystem target, Deque<StickySystem> order) {
    Iterator<StickySystem> i = order.iterator();
    while (i.hasNext()) {
      StickySystem s = i.next();
      if (target.uses(s) && !s.uses(target)) {
        i.remove();
        return s;
      }
      if (s.isUsedBy(target) && !target.isUsedBy(s)) {
        i.remove();
        return s;
      }

    }
    return target;
  }

  List<StickySystem> startOrder() {
    return systems;
  }

  List<StickySystem> shutdownOrder() {
    List<StickySystem> list = new ArrayList<StickySystem>(systems);
    Collections.reverse(list);
    return list;
  }

  public void start() {
    for (StickySystem system : startOrder()) {
      try {
        system.start();
      }
      catch (Exception e) {
        log.error("failed to start {}", system, e);
      }
    }
  }

  public void pause() {
    for (StickySystem system : startOrder()) {
      try {
        system.pause();
      }
      catch (Exception e) {
        log.error("failed to pause {}", system, e);
      }
    }
  }

  public void unpause() {
    for (StickySystem system : shutdownOrder()) {
      try {
        system.unpause();
      }
      catch (Exception e) {
        log.error("failed to unpause {}", system, e);
      }
    }
  }

  public void shutdown() {
    for (StickySystem system : shutdownOrder()) {
      try {
        system.shutdown();
      }
      catch (Exception e) {
        log.error("failed to stop {}", system, e);
      }
    }
  }
}
