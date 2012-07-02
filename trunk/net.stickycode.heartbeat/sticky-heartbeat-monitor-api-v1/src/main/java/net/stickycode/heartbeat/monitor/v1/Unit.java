package net.stickycode.heartbeat.monitor.v1;

public enum Unit {
  Time("seconds"),
  Force("newtons"),
  Mass("grams"),
  Distance("metre"),
  Count("events");

  private String description;

  public String description() {
    return description;
  };

  private Unit(String name) {
    this.description = name;
  }

}
