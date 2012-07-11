package co.nfigured.example.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import net.stickycode.stereotype.StickyComponent;

@Path("some")
@StickyComponent
public class SomeResources {

  @Inject
  ResourceHolder holder;

  @GET
  public SomeResource funny() {
    return holder.funny;
  }
  
//
//  @GET
//  public SomeResource brief() {
//    return holder.brief;
//  }
//
//  @GET
//  public SomeResource verbose() {
//    return holder.verbose;
//  }

}
