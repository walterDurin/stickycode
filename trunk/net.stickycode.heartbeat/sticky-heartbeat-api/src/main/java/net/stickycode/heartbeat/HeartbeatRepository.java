package net.stickycode.heartbeat;

import net.stickycode.stereotype.StickyFramework;


@StickyFramework
public interface HeartbeatRepository
    extends Iterable<Heartbeat> {

  void add(Heartbeat heartbeat);

}
