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
    return find(type, type, type, null, type.getSimpleName());
  }

  private static CoercionTarget find(Class<?> type, AnnotatedElement element, Class<?> owner, CoercionTarget parent, String name) {
    if (type.isArray())
      return new ArrayCoercionTarget(type, owner, parent, name);

    return new PrimitiveResolvingCoercionTarget(type, element, owner, parent, name);
  }

  public static CoercionTarget find(Type genericType, Class<?> owner, String name) {
    return find(null, genericType, null, owner, null, name);
  }

  public static CoercionTarget find(Class<?> type, Type genericType) {
    return find(type, genericType, type, type, null, type.getSimpleName());
  }

  @SuppressWarnings("rawtypes")
  static CoercionTarget find(Class<?> type, Type genericType, AnnotatedElement element, Class<?> owner, CoercionTarget parent,
      String name) {
    if (genericType instanceof Class)
      return find((Class<?>) genericType, element, owner, parent, name);

    if (genericType instanceof ParameterizedType)
      return new ParameterizedCoercionTarget((ParameterizedType) genericType, element, owner, parent, name);

    if (genericType instanceof GenericArrayType)
      return new ParameterizedArrayCoercionTarget((GenericArrayType) genericType, owner, parent, name);

    if (genericType instanceof TypeVariable)
      return resolveTypeVariable((TypeVariable) genericType, element, owner, parent, name);

    throw new CoercionTargetsDoesNotRecogniseTypeException(genericType);
  }

  private static CoercionTarget resolveTypeVariable(@SuppressWarnings("rawtypes") TypeVariable genericType,
      AnnotatedElement element, Class<?> owner,
      CoercionTarget parent, String name) {
    if (parent == null)
      throw new ParentCoercionTargetsAreRequiredToResolveTypeVariables(genericType, owner);

    if (genericType.getGenericDeclaration().equals(parent.getType()))
      for (TypeVariable<?> i : genericType.getGenericDeclaration().getTypeParameters()) {
        if (parent.getType().equals(i.getGenericDeclaration()))
          return find(parent.getComponentCoercionTypes()[0].getType(), element, owner, null, name);
      }

    for (Class<?> ip : ((Class<?>) genericType.getGenericDeclaration()).getInterfaces())
      if (parent.getType().equals(ip))
        for (TypeVariable<?> i : ip.getTypeParameters()) {
          if (parent.getType().equals(i.getGenericDeclaration()))
            return find(parent.getComponentCoercionTypes()[0].getType(), element, owner, null, name);
        }

    throw new CoercionTargetsDoesNotRecogniseTypeException(genericType);
  }

  public static CoercionTarget find(Field f) {
    return find(f.getType(), f.getGenericType(), f, f.getDeclaringClass(), null, f.getName());
  }

  public static CoercionTarget find(Method m) {
    return find(m.getReturnType(), m.getGenericReturnType(), m, m.getDeclaringClass(), null, m.getName());
  }

  public static CoercionTarget find(Field f, CoercionTarget parent) {
    return find(f.getType(), f.getGenericType(), f, f.getDeclaringClass(), parent, f.getName());
  }

}
