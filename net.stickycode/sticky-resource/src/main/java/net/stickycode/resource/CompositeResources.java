package net.stickycode.resource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompositeResources
    extends ResourceReferences {

  private List<ResourceReferences> resources = new LinkedList<ResourceReferences>();

  @Override
  public Iterator<ResourceReference> iterator() {
    return new CompositeIterator(resources.iterator());
  }

  @Override
  public String getReference() {
    return null;
  }

  public void add(ResourceReferences r) {
    resources.add(r);
  }

}
