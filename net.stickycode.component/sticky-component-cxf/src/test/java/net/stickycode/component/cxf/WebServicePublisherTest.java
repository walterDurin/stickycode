package net.stickycode.component.cxf;

import javax.jws.WebService;

import net.stickycode.component.cxf.v1.DummyWebService;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment({"/META-INF/cxf/cxf.xml", "/META-INF/cxf/cxf-servlet.xml", "/META-INF/cxf/cxf-extension-soap.xml"})
public class WebServicePublisherTest {

  @WebService
  public interface WS {
  }

  public class A
      implements WS {
  }

  @UnderTest
  WebServicePublisher publisher;

  @Test(expected = WebServiceShouldExistInVersionedPackageException.class)
  public void enforceVersionedPackages() {
    publisher.process(new A(), "A");
  }

  @Test
  public void check() {
    publisher.process(new DummyWebService(), "B");
  }
}
