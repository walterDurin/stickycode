package net.stickycode.coercion;

public class Primitives {

  public static Class<?> resolvePrimitive(Class<?> componentType) {
    if (!componentType.isPrimitive())
      return componentType;

    if (boolean.class.equals(componentType))
      return Boolean.class;

    if (int.class.equals(componentType))
      return Integer.class;

    if (float.class.equals(componentType))
      return Float.class;

    if (double.class.equals(componentType))
      return Double.class;

    if (byte.class.equals(componentType))
      return Byte.class;

    if (short.class.equals(componentType))
      return Short.class;

    throw new UnknownPrimitiveTypeException(componentType);
  }
}
