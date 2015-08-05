package net.stickycode.coercion.rs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestfulCoercionTest {

  @Failure(FailureClassification.NotFound)
  public static class NotFoundFailure
      extends RuntimeException {

  }

  public static class Blah {

  }

  public static class Concrete
      implements Contract {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Integer get(Integer id) {
      log.info("id {}", id);
      if (id.equals(404))
        throw new WebApplicationException(Response.status(404).header("X-Failure-Code", "NotFoundException").build());

      return id.intValue();
    }

  }

  @Path("/path/v1")
  public static interface Contract {

    @GET
    @Path("/{id}")
    public Integer get(@PathParam("id") Integer id)
        throws NotFoundFailure;

  }

  private static Server server;

  @Test
  public void isApplicable() {
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Contract.class))).isTrue();
  }

  @Test
  public void notApplicable() {
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Concrete.class))).isFalse();
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Integer.class))).isFalse();
  }

  @BeforeClass
  public static void before() {
    ProviderFactory.getSharedInstance().registerUserProvider(new FailureMapper());

    JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
    sf.setResourceClasses(Contract.class);
    sf.setResourceProvider(Contract.class, new SingletonResourceProvider(new Concrete()));
    sf.setAddress("http://localhost:8000");

    BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
    JAXRSBindingFactory factory = new JAXRSBindingFactory();
    factory.setBus(sf.getBus());
    manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
    server = sf.create();
  }

  @AfterClass
  public static void after() {
    if (server != null)
      server.destroy();
  }

  @Test
  public void get() throws IOException {
    Contract contract = proxy();
    assertThat(contract.get(1)).isEqualTo(1);
    assertThat(contract.get(10)).isEqualTo(10);
  }

  private Contract proxy() {
    Contract contract = (Contract) new RestfulCoercion().coerce(CoercionTargets.find(Contract.class), "http://localhost:8000/");
    return contract;
  }

  @Test
  public void notFound() {
    Contract contract = proxy();
    contract.get(404);
  }

}
