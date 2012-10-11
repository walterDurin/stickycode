package co.nfigured.example.spring3.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import net.stickycode.stereotype.StickyComponent;

@StickyComponent
@Path("someResource")
public class SomeResource {

  @GET
  public Name getName() {
    return new Name("My name is Slim Shady!");
  }
  
}