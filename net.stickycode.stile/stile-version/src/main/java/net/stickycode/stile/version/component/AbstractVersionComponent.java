package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;

public abstract class AbstractVersionComponent
    implements Comparable<AbstractVersionComponent> {

  private AbstractVersionComponent next;

  private final VersionString versionString;

  private CharacterVersionString qualifier;

  private ComponentOrdering ordering = ComponentOrdering.Release;

  public AbstractVersionComponent(VersionString versionString) {
    this.versionString = notNull(versionString, "Version specification cannot be null");
  }

  @Override
  public int compareTo(AbstractVersionComponent o) {
    int compareVersionString = versionString.toString().compareTo(o.versionString.toString());
    if (compareVersionString != 0)
      return compareVersionString;
    
    int compareOrdering = getOrdering().compareTo(o.getOrdering());
    if (compareOrdering != 0)
      return compareOrdering;

    return 0;
  }

  public ComponentOrdering getOrdering() {
    return ordering;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + valueHashCode();
    return result;
  }

  abstract protected int valueHashCode();

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    return valueEquals(obj);
  }

  abstract protected boolean valueEquals(Object other);

  AbstractVersionComponent getNext() {
    return next;
  }

  public void setNext(AbstractVersionComponent component) {
    next = component;
  }

  @Override
  public String toString() {
    if (qualifier != null)
      return versionString.toString() + qualifier.toString();

    return versionString.toString();
  }

  public void qualify(ComponentOrdering ordering, CharacterVersionString qualifier) {
    this.qualifier = qualifier;
    this.ordering = ordering;
  }
}
