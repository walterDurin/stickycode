package net.stickycode.rs.coercion.jersey;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Method;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.junit.Test;

@Path("/test/v1")
public class RequestMetadataTest {

  @GET
  @Path("/{id}")
  public Integer get(@PathParam("id") String id) {
    return null;
  }

  @Test
  public void operationPath() {
    assertThat(method().getOperationPath()).isEqualTo("/test/v1/a");
  }

  private RequestMetadata method() {
    return new RequestMetadata(field(), new Object[] { "a" });
  }

  private Method field() {
    try {
      return RequestMetadataTest.class.getDeclaredMethod("get", new Class[] { String.class });
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
  }
}
