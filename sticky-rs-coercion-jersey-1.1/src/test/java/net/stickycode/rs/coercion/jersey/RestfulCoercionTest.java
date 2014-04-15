package net.stickycode.rs.coercion.jersey;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.rs.coercion.jersey.value.v1.App;
import net.stickycode.rs.coercion.jersey.value.v1.Concrete;
import net.stickycode.rs.coercion.jersey.value.v1.Contract;
import net.stickycode.rs.coercion.jersey.value.v1.NotFoundFailure;
import net.stickycode.rs.coercion.jersey.value.v1.ValueObject;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.fest.assertions.Fail;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class RestfulCoercionTest {

  public static class Blah {

  }

  @Test
  public void isApplicable() {
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Contract.class))).isTrue();
  }

  @Test
  public void notApplicable() {
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Concrete.class))).isFalse();
    assertThat(new RestfulCoercion().isApplicableTo(CoercionTargets.find(Integer.class))).isFalse();
  }

  private static Server server;

  @BeforeClass
  public static void before()
      throws Exception {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("org.eclipse.jetty");
    if (!(logger instanceof ch.qos.logback.classic.Logger)) {
      return;
    }
    ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
    logbackLogger.setLevel(ch.qos.logback.classic.Level.WARN);

    server = new Server(9112);

    ServletContextHandler context = new ServletContextHandler();
    server.setHandler(context);

    ServletHolder holder = new ServletHolder(new ServletContainer(App.class));
    holder.setInitParameter(
        "com.sun.jersey.config.property.packages",
        "org.codehaus.jackson.jaxrs");
    holder.setInitParameter(
        "com.sun.jersey.spi.container.ContainerRequestFilters",
        "com.sun.jersey.api.container.filter.LoggingFilter");
    holder.setInitParameter(
        "com.sun.jersey.spi.container.ContainerResponseFilters",
        "com.sun.jersey.api.container.filter.LoggingFilter");
    context.addServlet(holder, "/*");

    server.start();
  }

  @AfterClass
  public static void after()
      throws Exception {
    if (server != null)
      server.stop();
  }

  @Test
  public void create() throws IOException {
    Contract contract = proxy();
    Integer id = contract.create(new ValueObject("create"));
    assertThat(contract.get(id)).isEqualTo(new ValueObject("create"));
    Integer id2 = contract.create(new ValueObject("new"));
    assertThat(contract.get(id2)).isEqualTo(new ValueObject("new"));
  }

  @Test
  public void delete() throws IOException {
    Contract contract = proxy();
    Integer id = contract.create(new ValueObject("temporary"));
    assertThat(contract.get(id)).isEqualTo(new ValueObject("temporary"));
    contract.delete(id);
    try {
      contract.get(id);
      Fail.fail();
    }
    catch (NotFoundFailure expected) {
      // expected
    }
  }

  private Contract proxy() {
    Contract contract = (Contract) new RestfulCoercion().coerce(CoercionTargets.find(Contract.class), "http://localhost:9112/");
    return contract;
  }

  @Test(expected = NotFoundFailure.class)
  public void notFound() {
    Contract contract = proxy();
    contract.get(404);
  }

}
