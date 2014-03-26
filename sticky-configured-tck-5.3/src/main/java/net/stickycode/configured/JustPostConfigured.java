package net.stickycode.configured;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.configured.PostConfigured;

public class JustPostConfigured {

  private boolean postConfigured = false;

  @PostConfigured
  public void postConfigured() {
    postConfigured = true;
  }
  
  public boolean isPostConfigured() {
    return postConfigured;
  }

}
