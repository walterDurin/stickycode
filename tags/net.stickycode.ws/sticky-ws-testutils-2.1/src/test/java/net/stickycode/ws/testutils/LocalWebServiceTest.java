package net.stickycode.ws.testutils;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.Uncontrolled;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.ws.testutils.v1.Example;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment("net.stickycode.ws")
@MockwireConfigured("localWebServiceTest.example=local://")
public class LocalWebServiceTest {

  @Configured
  LocalWebService<Example> example;
  
//  @UnderTest
//  RealExample real;
//  
//  @Uncontrolled
//  LocalWebServiceCoercion coercion;
  
  @Uncontrolled
  public Bus getBus() {
    return BusFactory.getDefaultBus();
  }

  @Test
  public void ok() {
    assertThat(example.getClient().invoke()).isTrue();
  }
}
