package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;

import net.stickycode.coercion.CoercionTarget;

public class ArrayCoercionTarget
    implements CoercionTarget {

  private final Class<?> type;

  public ArrayCoercionTarget(Class<?> type) {
    super();
    if (!type.isArray())
      throw new ArrayCoercionTargetsMustTargetArraysException(type);

    this.type = type;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean hasComponents() {
    return true;
  }

  @Override
  public CoercionTarget[] getComponentCoercionTypes() {
    return new CoercionTarget[] { CoercionTargets.find(type.getComponentType()) };
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public int hashCode() {
    return 31 * type.getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    ArrayCoercionTarget other = (ArrayCoercionTarget) obj;
    if (type == null) {
      if (other.type != null)
        return false;
    }

    return type == other.type;
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public Class<?> boxedType() {
    throw new UnsupportedOperationException("No boxed type here, move along please");
  }
  
  @Override
  public String toString() {
    return "COercionTarget{" + type.getSimpleName() + "}";
  }

  @Override
  public boolean canBeAnnotated() {
    return false;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    throw new UnsupportedOperationException("Arrays cannot be annotated directly");
  }

}
