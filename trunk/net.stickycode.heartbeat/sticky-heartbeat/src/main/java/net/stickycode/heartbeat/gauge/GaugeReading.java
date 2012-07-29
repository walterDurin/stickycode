package net.stickycode.heartbeat.gauge;

import net.stickycode.heartbeat.stereotype.Reading;

import org.joda.time.Instant;

public class GaugeReading
    implements Reading {

  private final Instant completedAt = new Instant();

  private final long elapsedNanos;

  private Long result;

  public GaugeReading(Long result, long nanosAtStart) {
    this.elapsedNanos = System.nanoTime() - nanosAtStart;
    this.result = result;
  }

}
