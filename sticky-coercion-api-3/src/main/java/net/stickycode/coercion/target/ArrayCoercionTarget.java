package net.stickycode.coercion.target;

import net.stickycode.coercion.CoercionTarget;

public class ArrayCoercionTarget
    extends AbstractCoercionTarget {

  public ArrayCoercionTarget(Class<?> type, Class<?> owner, CoercionTarget parent, String name) {
    super(type, owner, parent, name);
    if (!type.isArray())
      throw new ArrayCoercionTargetsMustTargetArraysException(type);
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
  public String toString() {
    return getClass().getSimpleName() + "{" + type.getSimpleName() + "}";
  }

}
