package net.stickycode.stile.version.component;


public interface Linked<NODE> {

  NODE getNext();

  void setNext(NODE node);

}
