package net.stickycode.resource;

import java.util.Iterator;

// TODO generalise
public class CompositeIterator
    implements Iterator<ResourceReference> {

  private Iterator<ResourceReferences> resourcesIterator;

  private Iterator<ResourceReference> current;

  public CompositeIterator(Iterator<ResourceReferences> iterator) {
    this.resourcesIterator = iterator;
  }

  @Override
  public boolean hasNext() {
    if (current != null && current.hasNext())
      return true;

    if (!resourcesIterator.hasNext())
      return false;

    current = resourcesIterator.next().iterator();

    return hasNext();
  }

  @Override
  public ResourceReference next() {
    return current.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("The composite iterator is immutable.");
  }

}
