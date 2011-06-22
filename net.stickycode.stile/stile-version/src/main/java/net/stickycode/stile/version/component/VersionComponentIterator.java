package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;

import java.util.Iterator;

public class VersionComponentIterator
    implements Iterator<AbstractVersionComponent> {

  private AbstractVersionComponent current;

  public VersionComponentIterator(AbstractVersionComponent head) {
    this.current = notNull(head, "First component cannot be null");
  }

  @Override
  public boolean hasNext() {
    return current != null;
  }

  @Override
  public AbstractVersionComponent next() {
    AbstractVersionComponent was = current;
    current = current.getNext();
    return was;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Version component iterator is immutable");
  }

}
