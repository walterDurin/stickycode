package net.stickycode.coercion.target;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import net.stickycode.coercion.CoercionTarget;

public class ParameterizedCoercionTarget
    extends AbstractCoercionTarget {

  private ParameterizedType parameterizedType;

  private AnnotatedElement annotatedElement;

  public ParameterizedCoercionTarget(ParameterizedType genericType, AnnotatedElement element, Class<?> owner, CoercionTarget parent, String name) {
    super((Class<?>) genericType.getRawType(), owner, parent, name);
    this.parameterizedType = genericType;
    this.annotatedElement = element;
  }

  @Override
  public boolean hasComponents() {
    return true;
  }

  @Override
  public CoercionTarget[] getComponentCoercionTypes() {
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    CoercionTarget[] targets = new CoercionTarget[actualTypeArguments.length];
    for (int i = 0; i < targets.length; i++) {
      targets[i] = CoercionTargets.find((Class<?>)parameterizedType.getRawType(), actualTypeArguments[i], annotatedElement, owner, parent, getName());
    }
    return targets;
  }

  @Override
  public int hashCode() {
    return 33 * getType().getClass().getName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    ParameterizedCoercionTarget other = (ParameterizedCoercionTarget) obj;
    if (parameterizedType == null) {
      if (other.parameterizedType != null)
        return false;
    }

    if (parameterizedType.getRawType() != other.parameterizedType.getRawType())
      return false;

    if (parameterizedType.getOwnerType() != other.parameterizedType.getOwnerType())
      return false;

    return Arrays.deepEquals(parameterizedType.getActualTypeArguments(), other.parameterizedType.getActualTypeArguments());
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
    return getClass().getSimpleName() + "{" + annotatedElement + " on " + owner.getSimpleName() + "}";
  }

}
