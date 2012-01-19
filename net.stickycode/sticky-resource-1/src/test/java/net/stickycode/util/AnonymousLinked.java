package net.stickycode.util;

import net.stickycode.util.Linked;


public class AnonymousLinked
    implements Linked<AnonymousLinked> {

  private AnonymousLinked next;

  @Override
  public AnonymousLinked getNext() {
    return next;
  }

  @Override
  public void setNext(AnonymousLinked node) {
    this.next = node;
  }
}