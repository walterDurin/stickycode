package net.stickycode.bootstrap;

import java.util.Iterator;

public class ArrayIterator<T>
    implements Iterator<T> {

  private T[] a;

  private int currentIndex = 0;

  public ArrayIterator(T[] a) {
    this.a = a;
  }

  @Override
  public boolean hasNext() {
    return currentIndex < a.length;
  }

  @Override
  public T next() {
    return a[currentIndex++];
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Cannot remove from the array");
  }

}
