package net.stickycode.heartbeat.monitor.v1;

import javax.xml.bind.annotation.XmlElement;

public class Gauge {

  @XmlElement(required = true)
  private String name;

  private Reading high;

  private Reading low;

  private Reading mean;

  public Reading getLow() {
    return low;
  }

  public String getName() {
    return name;
  }

  public Reading getMean() {
    return mean;
  }

  public Reading getHigh() {
    return high;
  }

  public Gauge withName(String name) {
    this.name = name;
    return this;
  }
}
