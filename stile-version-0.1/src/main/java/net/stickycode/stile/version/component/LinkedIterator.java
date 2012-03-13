package net.stickycode.stile.version.component;

import java.util.Iterator;

public class LinkedIterator<NODE extends Linked<NODE>>
    implements Iterator<NODE> {

  private NODE linked;

  /**
   * Create a new linker iterator starting at the given node, if the node is null then the iterator will be empty
   */
  public LinkedIterator(NODE linked) {
    this.linked = linked; // if its null iteration is empty
  }

  @Override
  public boolean hasNext() {
    return linked != null;
  }

  @Override
  public NODE next() {
    NODE previous = linked;
    linked = linked.getNext();
    return previous;
  }

  @Override
  public void remove() {
  }

}
