package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.stickycode.coercion.CoercionTarget;

public class CoercionTargets {

  public static CoercionTarget find(Class<?> type) {
    return find(type, type, type);
  }

  private static CoercionTarget find(Class<?> type, AnnotatedElement element, Class<?> owner) {
    if (type.isArray())
      return new ArrayCoercionTarget(type, owner);

    return new PrimitiveResolvingCoercionTarget(type, element, owner);
  }

  public static CoercionTarget find(Type genericType) {
    return find(genericType, null, null);
  }

  private static CoercionTarget find(Type genericType, AnnotatedElement element, Class<?> owner) {
    if (genericType instanceof Class)
      return find((Class<?>) genericType, element, owner);

    if (genericType instanceof ParameterizedType)
      return new ParameterizedCoercionTarget((ParameterizedType) genericType, element, owner);

    if (genericType instanceof GenericArrayType)
      return new ParameterizedArrayCoercionTarget((GenericArrayType)genericType);
    
    throw new CoercionTargetsDoesNotRecogniseTypeException(genericType);
  }

  public static CoercionTarget find(Field f) {
    return find(f.getGenericType(), f, f.getDeclaringClass());
  }
  
  public static CoercionTarget find(Method m) {
    return find(m.getGenericReturnType(), m, m.getDeclaringClass());
  }

}
