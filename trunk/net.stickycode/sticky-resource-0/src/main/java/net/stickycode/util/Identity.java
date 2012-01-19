package net.stickycode.util;

public interface Identity<T>
    extends Comparable<T> {

  int hashCode();

  boolean eq(T t);

}
