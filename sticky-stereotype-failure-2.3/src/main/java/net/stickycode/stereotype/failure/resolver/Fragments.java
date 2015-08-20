package net.stickycode.stereotype.failure.resolver;

import java.util.Iterator;

public class Fragments
    implements Iterable<Fragment> {

  private String message;

  public Fragments(String message) {
    this.message = message;
  }

  @Override
  public Iterator<Fragment> iterator() {
    return new FragmentIterator(message);
  }

}
