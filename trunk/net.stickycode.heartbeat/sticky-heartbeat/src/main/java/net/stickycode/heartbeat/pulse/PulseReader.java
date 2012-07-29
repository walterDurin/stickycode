package net.stickycode.heartbeat.pulse;

import net.stickycode.heartbeat.FailedGaugeReading;
import net.stickycode.heartbeat.IncompleteGaugeReading;
import net.stickycode.heartbeat.stereotype.GaugeReader;
import net.stickycode.heartbeat.stereotype.PulseMeasurement;
import net.stickycode.heartbeat.stereotype.Reading;

public class PulseReader {

  private long nanosAtStart = System.nanoTime();

  public Reading fail() {
    return new IncompleteGaugeReading(nanosAtStart);
  }

  public Reading take(PulseMeasurement result) {
    return new PulseReading(result, nanosAtStart);
  }

  public Reading fail(Throwable e) {
    return new FailedGaugeReading(e, nanosAtStart);
  }

}
