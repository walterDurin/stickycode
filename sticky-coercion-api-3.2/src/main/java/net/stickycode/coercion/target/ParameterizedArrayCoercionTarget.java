package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericArrayType;

import net.stickycode.coercion.CoercionTarget;

public class ParameterizedArrayCoercionTarget
    implements CoercionTarget {

  private final GenericArrayType type;

  public ParameterizedArrayCoercionTarget(GenericArrayType genericType) {
    this.type = genericType;
  }

  @Override
  public Class<?> getType() {
    throw new UnsupportedOperationException("Generic array types don't have a type");
  }

  @Override
  public boolean hasComponents() {
    return true;
  }

  @Override
  public CoercionTarget[] getComponentCoercionTypes() {
    return new CoercionTarget[] { CoercionTargets.find(type.getGenericComponentType()) };
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public int hashCode() {
    return 31 * type.getGenericComponentType().getClass().getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    ParameterizedArrayCoercionTarget other = (ParameterizedArrayCoercionTarget) obj;
    if (type == null) {
      if (other.type != null)
        return false;
    }
    
    return CoercionTargets.find(type.getGenericComponentType()).equals(
        CoercionTargets.find(other.type.getGenericComponentType()));
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
  public boolean canBeAnnotated() {
    return false;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    throw new UnsupportedOperationException("Arrays cannot be annotated directly");
  }
}
