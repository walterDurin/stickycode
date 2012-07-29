package net.stickycode.heartbeat;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockwireRunner.class)
@MockwireContainment({"net.stickycode.heartbeat", "net.stickycode.scheduled"})
@MockwireConfigured({
  "beanWithHeartbeat.fuelGauge.schedule=every 50 milliseconds",
  "beanWithHeartbeat.heartbeat.schedule=every 50 milliseconds"
  
})
public class HeartbeatTest {
  
  private Logger log = LoggerFactory.getLogger(getClass());
  
  @UnderTest
  private BeanWithHeartbeat beanWithHeartbeat;
  
  @Inject
  HeartbeatRepository repository;
  
  @Inject
  ConfigurationSystem system;
  
  @Test
  public void blah() throws InterruptedException {
    system.start();
    Thread.sleep(300);
    for (Heartbeat beat : repository) {
      log.info("beat {} is alive {}", beat.getLabel(), beat.isAlive());
    }
  }
}
