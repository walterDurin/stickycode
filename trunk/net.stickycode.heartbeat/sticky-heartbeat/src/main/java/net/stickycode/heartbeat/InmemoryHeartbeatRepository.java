package net.stickycode.heartbeat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class InmemoryHeartbeatRepository
    implements HeartbeatRepository {

  private List<Heartbeat> heartbeats = new ArrayList<Heartbeat>();

  @Override
  public Iterator<Heartbeat> iterator() {
    return heartbeats.iterator();
  }

  @Override
  public void add(Heartbeat heartbeat) {
    heartbeats.add(heartbeat);
  }

}
