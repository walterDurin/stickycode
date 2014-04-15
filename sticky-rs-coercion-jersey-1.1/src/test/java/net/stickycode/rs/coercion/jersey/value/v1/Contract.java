package net.stickycode.rs.coercion.jersey.value.v1;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/path/v1")
public interface Contract {

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ValueObject get(@PathParam("id") Integer id)
      throws NotFoundFailure;

  @POST
  public Integer create(ValueObject value)
      throws GatewayFailure;

  @DELETE
  @Path("/{id}")
  public void delete(@PathParam("id") Integer id)
      throws NotFoundFailure;

}
