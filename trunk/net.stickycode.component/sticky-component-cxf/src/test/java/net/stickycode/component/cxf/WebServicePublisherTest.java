package net.stickycode.component.cxf;

import javax.inject.Inject;
import javax.jws.WebService;

import net.stickycode.component.cxf.v1.DummyWebService;
import net.stickycode.component.cxf.v1.WebServiceContract;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment({"/META-INF/cxf/cxf.xml", "/META-INF/cxf/cxf-servlet.xml"})
public class WebServicePublisherTest {

  @WebService
  public interface WS {
  }

  public class A
      implements WS {
  }

  @Inject
  WebServiceExposureRepository repository;
  
  @UnderTest
  WebServicePublisher publisher;

  @Test(expected = WebServiceShouldExistInVersionedPackageException.class)
  public void enforceVersionedPackages() {
    repository.add(new A(), WS.class);
    publisher.exposeWebservices();
  }

  @Test
  public void check() {
    repository.add(new DummyWebService(), WebServiceContract.class);
    publisher.exposeWebservices();
  }
}
