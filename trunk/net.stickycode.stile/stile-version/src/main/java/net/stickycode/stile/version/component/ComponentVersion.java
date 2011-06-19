package net.stickycode.stile.version.component;

import java.util.Iterator;
import java.util.List;

import net.stickycode.stile.version.Version;

public class ComponentVersion
    implements Iterable<AbstractVersionComponent>, Version {

  private final List<AbstractVersionComponent> components;

  public ComponentVersion(List<AbstractVersionComponent> components) {
    super();
    this.components = components;
  }

  @Override
  public Iterator<AbstractVersionComponent> iterator() {
    return components.iterator();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((components == null) ? 0 : components.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComponentVersion other = (ComponentVersion) obj;
    if (components == null) {
      if (other.components != null)
        return false;
    }
    else
      if (!components.equals(other.components))
        return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    Iterator<AbstractVersionComponent> i = components.iterator();
    b.append(i.next());
    while (i.hasNext()) {
      b.append('.').append(i.next());
    }
    return b.toString();
  }

  @Override
  public int compareTo(Version o) {
    if (this == o)
      return 0;
    
    if (getClass() != o.getClass())
      throw new RuntimeException(getClass() + " != " + o.getClass()); 
      
    Iterator<AbstractVersionComponent> other = ((ComponentVersion)o).iterator();
    for (AbstractVersionComponent t : components) {
      if (!other.hasNext())
        return 1;
      
      int compare = t.compareTo(other.next());
      if (compare != 0)
        return compare;
    }
    
    if (other.hasNext())
      return -1;

    return 0;
  }

}
