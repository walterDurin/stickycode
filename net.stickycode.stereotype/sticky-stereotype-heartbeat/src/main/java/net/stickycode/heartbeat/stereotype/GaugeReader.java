package net.stickycode.heartbeat.stereotype;

public interface GaugeReader {

  Reading fail();

  Reading take(Long result);

  Reading fail(Throwable e);

}
