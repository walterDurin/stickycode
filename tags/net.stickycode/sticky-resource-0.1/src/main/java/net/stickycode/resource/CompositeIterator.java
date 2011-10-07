package net.stickycode.resource;

import java.util.Iterator;

// TODO generalise
public class CompositeIterator
    implements Iterator<Resource> {

  private Iterator<Resources> resourcesIterator;

  private Iterator<Resource> current;

  public CompositeIterator(Iterator<Resources> iterator) {
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
  public Resource next() {
    return current.next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("The composite iterator is immutable.");
  }

}
