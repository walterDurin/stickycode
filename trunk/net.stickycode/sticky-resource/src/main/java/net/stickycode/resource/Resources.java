package net.stickycode.resource;

import java.util.Iterator;

abstract public class Resources
    implements Iterable<Resource> {

  @Override
  abstract public Iterator<Resource> iterator();

}
