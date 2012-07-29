package net.stickycode.heartbeat;

import net.stickycode.heartbeat.stereotype.Gauge;
import net.stickycode.heartbeat.stereotype.GaugeReader;
import net.stickycode.heartbeat.stereotype.Pulse;
import net.stickycode.heartbeat.stereotype.PulseMeasurement;
import net.stickycode.heartbeat.stereotype.Reading;

public class BeanWithHeartbeat {

  private Integer beatsLeft = 10;
  private Integer fuelUsed = 0;

  @Pulse
  public PulseMeasurement heartbeat() {
    beatsLeft--;
    if (beatsLeft < 1)
      return PulseMeasurement.NoPulse;

    if (beatsLeft < 2)
      return PulseMeasurement.Critical;

    if (beatsLeft == 5)
      throw new RuntimeException("Wheres the pulse?");
    
    return PulseMeasurement.Normal;
  }
  
  @Gauge("litres")
  public Reading fuelGauge(GaugeReader reader) {
    if (fuelUsed > 10)
      return reader.fail();
    
    long left = 10 / (10 - fuelUsed++);
    
    return reader.take(left);
  }

}
