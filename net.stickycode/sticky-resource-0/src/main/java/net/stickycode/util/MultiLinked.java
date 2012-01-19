package net.stickycode.util;

import static net.stickycode.exception.Preconditions.notNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultiLinked<KEY, ELEMENT extends Linked<ELEMENT>> {

  private Map<KEY, ELEMENT> heads = new HashMap<KEY, ELEMENT>();
  private Map<KEY, ELEMENT> tails = new HashMap<KEY, ELEMENT>();

  public Iterator<ELEMENT> iterate(KEY key) {
    KEY notNull = notNull(key, "Key for iteration cannot be null");
    return new LinkedIterator<ELEMENT>(heads.get(notNull));
  }
  
  public Iterable<ELEMENT> iterable(KEY key) {
    final KEY notNull = notNull(key, "Key for iteration cannot be null");
    return new Iterable<ELEMENT>() {
      @Override
      public Iterator<ELEMENT> iterator() {
        return iterate(notNull);
      }
    };
  }

  public void put(KEY key, ELEMENT element) {
    ELEMENT tail = tails.get(key);
    if (tail != null) {
      tails.put(key, element);
      tail.setNext(element);
    }
    else {
      heads.put(key, element);
      tails.put(key, element);
    }
  }

}
