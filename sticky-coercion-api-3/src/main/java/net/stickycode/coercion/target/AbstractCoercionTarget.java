package net.stickycode.coercion.target;

import static net.stickycode.exception.Preconditions.notBlank;
import static net.stickycode.exception.Preconditions.notNull;

import java.lang.reflect.AnnotatedElement;

import net.stickycode.coercion.CoercionTarget;

public abstract class AbstractCoercionTarget
    implements CoercionTarget {

  protected final Class<?> type;

  protected final Class<?> owner;

  protected final CoercionTarget parent;
  
  private final String name;

  public AbstractCoercionTarget(Class<?> type, Class<?> owner, CoercionTarget parent, String name) {
    super();
    this.type = notNull(type, "All coercion targets must have a type");
    this.owner = notNull(owner, "All coercion targets must have an owner");
    this.parent = parent;
    this.name = notBlank(name, "A conercion target name cannot be blank");
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
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public Class<?> boxedType() {
    throw new UnsupportedOperationException("This target is not primitive hence no boxing " + this);
  }

  @Override
  public boolean canBeAnnotated() {
    return false;
  }

  @Override
  public AnnotatedElement getAnnotatedElement() {
    throw new UnsupportedOperationException("There are no components on " + getClass());
  }

  @Override
  public Class<?> getOwner() {
    return owner;
  }

  @Override
  public CoercionTarget getParent() {
    return parent;
  }

  @Override
  public boolean hasParent() {
    return parent != null;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + type.getSimpleName() + "}";
  }

  public String getName() {
    return name;
  }

}
