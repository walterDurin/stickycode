package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.Primitives;

public class PrimitiveResolvingCoercionTarget
    extends AbstractCoercionTarget {

  private AnnotatedElement annotatedElement;

  public PrimitiveResolvingCoercionTarget(Class<?> type, AnnotatedElement annotatedElement, Class<?> owner, CoercionTarget parent, String name) {
    super(type, owner, parent, name);
    this.annotatedElement = annotatedElement;
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
  public String toString() {
    if (isPrimitive())
      return "CoercionTarget{" + boxedType().getSimpleName() + " => " + type.getSimpleName() + "}";

    return "CoercionTarget{" + type.getSimpleName() + "}";
  }
}
