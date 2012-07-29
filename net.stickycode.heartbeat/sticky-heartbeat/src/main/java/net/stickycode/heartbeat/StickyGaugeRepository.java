package net.stickycode.heartbeat;

import java.util.ArrayList;
import java.util.List;

import net.stickycode.stereotype.component.StickyRepository;

@StickyRepository
public class StickyGaugeRepository
    implements GaugeRepository {

  private List<GaugeChart> readings = new ArrayList<GaugeChart>();

  @Override
  public void add(GaugeChart chart) {
    readings.add(chart);
  }

}
