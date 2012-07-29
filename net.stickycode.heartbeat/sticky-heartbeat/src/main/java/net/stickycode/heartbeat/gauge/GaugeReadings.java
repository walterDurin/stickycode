package net.stickycode.heartbeat.gauge;

import net.stickycode.heartbeat.GaugeChart;
import net.stickycode.heartbeat.stereotype.Reading;

public class GaugeReadings implements GaugeChart{

  private String label;

  public GaugeReadings(String label) {
    this.label = label;
  }

  public void add(Reading reading) {
  }

  public String getLabel() {
    return label;
  }

}
