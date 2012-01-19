package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.Primitives;

public class PrimitiveResolvingCoercionTarget
    implements CoercionTarget {

  private final Class<?> type;

  private AnnotatedElement annotatedElement;

  private final Class<?> owner;

  public PrimitiveResolvingCoercionTarget(Class<?> type, AnnotatedElement annotatedElement, Class<?> owner) {
    super();
    this.type = type;
    this.annotatedElement = annotatedElement;
    this.owner = owner;
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public boolean hasComponents() {
    return false;
  }

  @Override
  public CoercionTarget[] getComponentCoercionTypes() {
    throw new UnsupportedOperationException("There are no components on " + getClass());
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

    PrimitiveResolvingCoercionTarget other = (PrimitiveResolvingCoercionTarget) obj;
    if (type == null) {
      if (other.type != null)
        return false;
    }

    return type.equals(other.type);
  }

  @Override
  public boolean isPrimitive() {
    return type.isPrimitive();
  }

  @Override
  public Class<?> boxedType() {
    return Primitives.resolvePrimitive(type);
  }

  @Override
  public boolean canBeAnnotated() {
    return annotatedElement != null;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    return annotatedElement;
  }

  @Override
  public Class<?> getOwner() {
    return owner;
  }

  @Override
  public String toString() {
    if (isPrimitive())
      return "CoercionTarget{" + boxedType().getSimpleName() + " => " + type.getSimpleName() + "}";

    return "CoercionTarget{" + type.getSimpleName() + "}";
  }
}
