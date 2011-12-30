package net.stickycode.coercion.target;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.stickycode.coercion.CoercionTarget;

public class CoercionTargets {

  public static CoercionTarget find(Class<?> type) {
    if (type.isArray())
      return new ArrayCoercionTarget(type);

    return new PrimitiveResolvingCoercionTarget(type);
  }

  public static CoercionTarget find(Type genericType) {
    if (genericType instanceof Class)
      return find((Class<?>) genericType);

    if (genericType instanceof ParameterizedType)
      return new ParameterizedCoercionTarget((ParameterizedType) genericType);

    if (genericType instanceof GenericArrayType)
      return new ParameterizedArrayCoercionTarget((GenericArrayType)genericType);
    
    throw new RuntimeException("Dont' know how to deal with generic type " + genericType);
  }

  public static CoercionTarget find(Field f) {
    return find(f.getGenericType());
  }

}
