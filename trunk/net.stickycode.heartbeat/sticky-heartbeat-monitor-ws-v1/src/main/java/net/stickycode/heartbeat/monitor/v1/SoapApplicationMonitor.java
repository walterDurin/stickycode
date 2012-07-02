package net.stickycode.heartbeat.monitor.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.cxf.annotations.Logging;
import org.apache.cxf.annotations.SchemaValidation;

import net.stickycode.heartbeat.Heartbeat;
import net.stickycode.heartbeat.HeartbeatRepository;
import net.stickycode.stereotype.component.StickyService;

@StickyService
@SchemaValidation
@Logging(pretty = true)
public class SoapApplicationMonitor
    implements ApplicationMonitor {

  @Inject
  HeartbeatRepository heartBeats;

  @Override
  public boolean isAlive() {
    for (Heartbeat h : heartBeats) {
      if (!h.isAlive())
        return false;
    }

    return true;
  }

  @Override
  public List<Gauge> measure() {
    List<Gauge> gauges = new ArrayList<Gauge>();
    for (Heartbeat h : heartBeats) {
      gauges.add(new Gauge().withName(h.getLabel()));
    }
    return gauges;
  }

  @Override
  public List<Gauge> history(Integer startOffsetInSeconds, Integer endOffsetInSeconds) {
    return Collections.singletonList(new Gauge());
  }

}
