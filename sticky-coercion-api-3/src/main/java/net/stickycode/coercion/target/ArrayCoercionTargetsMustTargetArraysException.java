package net.stickycode.coercion.target;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class ArrayCoercionTargetsMustTargetArraysException
    extends PermanentException {

  public ArrayCoercionTargetsMustTargetArraysException(Class<?> type) {
    super("Expected an array but was given {} instead", type);
  }

}
