package net.stickycode.heartbeat.gauge;

import net.stickycode.heartbeat.FailedGaugeReading;
import net.stickycode.heartbeat.IncompleteGaugeReading;
import net.stickycode.heartbeat.stereotype.GaugeReader;
import net.stickycode.heartbeat.stereotype.Reading;

public class HeartbeatGaugeReader
    implements GaugeReader {

  private long nanosAtStart = System.nanoTime();

  @Override
  public Reading fail() {
    return new IncompleteGaugeReading(nanosAtStart);
  }

  @Override
  public Reading take(Long result) {
    return new GaugeReading(result, nanosAtStart);
  }

  @Override
  public Reading fail(Throwable e) {
    return new FailedGaugeReading(e, nanosAtStart);
  }

}
