package net.stickycode.component.cxf;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.component.cxf.WebServicePublisher;
import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.junit4.MockwireRunner;


@RunWith(MockwireRunner.class)
public class WebServicePublisherTest {


  @WebService
  public interface WS {

  }

  public class A implements WS {

  }

  @Bless
  public Bus bus() {
    Bus bus = BusFactory.newInstance().createBus();
    bus.getExtension(DestinationFactoryManager.class)
    .registerDestinationFactory(SoapTransportFactory.SOAP_11_HTTP_BINDING, createDestinationFactory());
    return bus;
  }

  private DestinationFactory createDestinationFactory() {
    return new DestinationFactory() {

      @Override
      public Set<String> getUriPrefixes() {
        return Collections.emptySet();
      }

      @Override
      public List<String> getTransportIds() {
        return Collections.singletonList(SoapTransportFactory.SOAP_11_HTTP_BINDING);
      }

      @Override
      public Destination getDestination(EndpointInfo arg0) throws IOException {
        return new Destination() {

          @Override
          public void setMessageObserver(MessageObserver arg0) {
          }

          @Override
          public void shutdown() {
          }

          @Override
          public MessageObserver getMessageObserver() {
            return null;
          }

          @Override
          public Conduit getBackChannel(Message arg0, Message arg1, EndpointReferenceType arg2) throws IOException {
            return null;
          }

          @Override
          public EndpointReferenceType getAddress() {
            return null;
          }
        };
      }
    };
  }

  @Bless
  WebServicePublisher publisher;

  @Test
  public void check() {
    publisher.process(new A(), "A");
  }
}
