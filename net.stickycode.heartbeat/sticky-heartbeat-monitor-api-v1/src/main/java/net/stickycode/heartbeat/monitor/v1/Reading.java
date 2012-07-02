package net.stickycode.heartbeat.monitor.v1;

import java.math.BigDecimal;


public class Reading {

  private BigDecimal value;

  private Unit unit;
  
  public BigDecimal getValue() {
    return value;
  }
  
  public void setValue(BigDecimal value) {
    this.value = value;
  }
  
  public Unit getUnit() {
    return unit;
  }
  
  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
