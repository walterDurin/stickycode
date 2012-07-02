package net.stickycode.heartbeat.monitor.v1;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;
import javax.xml.ws.soap.SOAPFaultException;

import net.stickycode.coercion.ws.harness.WebServiceEndpoint;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.Configured;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireConfigured({
    "applicationMonitorIntegrationTest.endpoint=local://blah",
    "blah.blah.schedule=every 2 seconds" })
@MockwireContainment({ "net.stickycode.heartbeat", "net.stickycode.scheduled" })
public class ApplicationMonitorIntegrationTest {

  @Inject
  Blah blah;

  @Configured
  WebServiceEndpoint<ApplicationMonitor> endpoint;

  @Test
  public void up() {
    try {
      assertThat(endpoint).isNotNull();
      assertThat(endpoint.getClient().isAlive()).isTrue();
      assertThat(endpoint.getClient().measure()).isNotEmpty();
    }
    finally {
      endpoint.stop();
    }
  }

  @Test(expected = SOAPFaultException.class)
  public void ug() {
    try {
      assertThat(endpoint).isNotNull();
      assertThat(endpoint.getClient().history(1, 5)).isNotEmpty();
    }
    finally { 
      endpoint.stop();
    }
  }

}
