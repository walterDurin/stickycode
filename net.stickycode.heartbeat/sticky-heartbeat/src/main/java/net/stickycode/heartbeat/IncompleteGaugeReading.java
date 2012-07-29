package net.stickycode.heartbeat;

import net.stickycode.heartbeat.stereotype.Reading;

import org.joda.time.Instant;


public class IncompleteGaugeReading
    implements Reading {
  
  private Throwable failure;
  
  private final Instant completedAt = new Instant();

  private final long elapsedNanos;

  public IncompleteGaugeReading( long nanosAtStart) {
    this.elapsedNanos = System.nanoTime() - nanosAtStart;
  }

}
