package co.nfigured.example.resource;

import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.StickyComponent;


@StickyComponent
public class ResourceHolder {

  @Configured
  SomeResource verbose;
  
  @Configured
  SomeResource brief;
  
  @Configured
  SomeResource funny;
  
}
