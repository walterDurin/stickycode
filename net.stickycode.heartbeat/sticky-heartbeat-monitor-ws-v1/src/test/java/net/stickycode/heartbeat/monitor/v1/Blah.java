package net.stickycode.heartbeat.monitor.v1;

import net.stickycode.stereotype.Pulse;
import net.stickycode.stereotype.component.StickyService;


@StickyService
public class Blah {

  @Pulse
  public Boolean blah() {
    return true;
  }
}
