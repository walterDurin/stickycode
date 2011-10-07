package net.stickycode.resource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompositeResources
    extends Resources {

  private List<Resources> resources = new LinkedList<Resources>();

  @Override
  public Iterator<Resource> iterator() {
    return new CompositeIterator(resources.iterator());
  }

  @Override
  public String getReference() {
    return null;
  }

  public void add(Resources r) {
    resources.add(r);
  }

}
