package net.stickycode.heartbeat.pulse;

import net.stickycode.heartbeat.stereotype.PulseMeasurement;
import net.stickycode.heartbeat.stereotype.Reading;

import org.joda.time.Instant;

public class PulseReading
    implements Reading {

  private final Instant completedAt = new Instant();

  private final long elapsedNanos;

  private PulseMeasurement result;

  public PulseReading(PulseMeasurement result, long nanosAtStart) {
    this.elapsedNanos = System.nanoTime() - nanosAtStart;
    this.result = result;
  }
}
