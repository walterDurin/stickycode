package net.stickycode.resource.protocol.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("resource/{id}")
public class WritableTestResource {

  private static Map<String, String> map = new HashMap<String, String>();

  @PUT
  public Response put(@PathParam("id") String id, String value) {
    map.put(id, value);
    try {
      return Response.created(new URI("http://blah/" + id)).build();
    }
    catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @GET
  public String get(@PathParam("id") String id) {
    return map.get(id);
  }
}
