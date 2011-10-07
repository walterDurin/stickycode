package net.stickycode.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Sequence<ELEMENT extends Linked<ELEMENT>>
    implements List<ELEMENT> {
  
  private final ELEMENT head;
  public Sequence(ELEMENT head) {
    this.head = head;
  }

  private ELEMENT tail;
  private int size = 0;

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Iterator<ELEMENT> iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return null;
  }

  @Override
  public boolean add(ELEMENT e) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends ELEMENT> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends ELEMENT> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
  }

  @Override
  public ELEMENT get(int index) {
    return null;
  }

  @Override
  public ELEMENT set(int index, ELEMENT element) {
    return null;
  }

  @Override
  public void add(int index, ELEMENT element) {
  }

  @Override
  public ELEMENT remove(int index) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public ListIterator<ELEMENT> listIterator() {
    return null;
  }

  @Override
  public ListIterator<ELEMENT> listIterator(int index) {
    return null;
  }

  @Override
  public List<ELEMENT> subList(int fromIndex, int toIndex) {
    return null;
  }

}
