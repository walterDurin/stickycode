package net.stickycode.coercion.target;

import java.lang.reflect.Type;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class CoercionTargetsDoesNotRecogniseTypeException
    extends PermanentException {

  public CoercionTargetsDoesNotRecogniseTypeException(Type genericType) {
    super("The coercion targets finder does not recognise type '{}'", genericType);
  }

}
