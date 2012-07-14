package net.stickycode.bootstrap;

import java.util.AbstractSet;
import java.util.Iterator;


public class ArraySet<T>
    extends AbstractSet<T>
{

  private T[] a;

  public ArraySet(T[] a) {
    super();
    this.a = a;
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayIterator<T>(a);
  }

  @Override
  public int size() {
    return a.length;
  }

}
