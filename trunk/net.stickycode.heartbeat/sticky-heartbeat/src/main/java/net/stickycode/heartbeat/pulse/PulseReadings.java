package net.stickycode.heartbeat.pulse;

import java.beans.Introspector;

import net.stickycode.heartbeat.Heartbeat;
import net.stickycode.heartbeat.stereotype.Reading;

public class PulseReadings
    implements Heartbeat {
  
  private final String label;

  public PulseReadings(String label) {
    this.label = label;
  }

  public void add(Reading pulse) {
  }

  @Override
  public boolean isAlive() {
    return false;
  }

  @Override
  public String getLabel() {
    return label;
  }
}
