package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.stickycode.coercion.CoercionTarget;

public class CoercionTargets {

  public static CoercionTarget find(Class<?> type) {
    return find(type, type, type, null);
  }

  private static CoercionTarget find(Class<?> type, AnnotatedElement element, Class<?> owner, CoercionTarget parent) {
    if (type.isArray())
      return new ArrayCoercionTarget(type, owner, parent);

    return new PrimitiveResolvingCoercionTarget(type, element, owner, parent);
  }

  public static CoercionTarget find(Type genericType, Class<?> owner) {
    return find(null, genericType, null, owner, null);
  }
  
  public static CoercionTarget find(Class<?> type, Type genericType) {
    return find(type, genericType, type, type, null);
  }

  @SuppressWarnings("rawtypes")
  static CoercionTarget find(Class<?> type, Type genericType, AnnotatedElement element, Class<?> owner, CoercionTarget parent) {
    if (genericType instanceof Class)
      return find((Class<?>) genericType, element, owner, parent);

    if (genericType instanceof ParameterizedType)
      return new ParameterizedCoercionTarget((ParameterizedType) genericType, element, owner, parent);

    if (genericType instanceof GenericArrayType)
      return new ParameterizedArrayCoercionTarget((GenericArrayType) genericType, owner, parent);

    if (genericType instanceof TypeVariable)
      return resolveTypeVariable((TypeVariable) genericType, element, owner, parent);

    throw new CoercionTargetsDoesNotRecogniseTypeException(genericType);
  }

  private static CoercionTarget resolveTypeVariable(@SuppressWarnings("rawtypes") TypeVariable genericType, AnnotatedElement element, Class<?> owner,
      CoercionTarget parent) {
    if (parent == null)
      throw new ParentCoercionTargetsAreRequiredToResolveTypeVariables(genericType, owner);

    if (genericType.getGenericDeclaration().equals(parent.getType()))
      for (TypeVariable<?> i : genericType.getGenericDeclaration().getTypeParameters()) {
        if (parent.getType().equals(i.getGenericDeclaration()))
          return find(parent.getComponentCoercionTypes()[0].getType(), element, owner, null);
      }

    throw new CoercionTargetsDoesNotRecogniseTypeException(genericType);
  }

  public static CoercionTarget find(Field f) {
    return find(f.getType(), f.getGenericType(), f, f.getDeclaringClass(), null);
  }

  public static CoercionTarget find(Method m) {
    return find(m.getReturnType(), m.getGenericReturnType(), m, m.getDeclaringClass(), null);
  }

  public static CoercionTarget find(Field f, CoercionTarget parent) {
    return find(f.getType(), f.getGenericType(), f, f.getDeclaringClass(), parent);
  }

}
