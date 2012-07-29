package net.stickycode.heartbeat;

import net.stickycode.heartbeat.stereotype.Reading;


public class FailedGaugeReading
    implements Reading {

  private Throwable failure;
  private long elapsedNanos;
  
  public FailedGaugeReading(Throwable t, long nanosAtStart) {
    this.elapsedNanos = System.nanoTime() - nanosAtStart;
    this.failure = t;
  }

}
