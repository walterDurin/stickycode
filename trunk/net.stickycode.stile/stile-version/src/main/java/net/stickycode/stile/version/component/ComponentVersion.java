package net.stickycode.stile.version.component;

import java.util.Iterator;

import net.stickycode.stile.version.Version;

public class ComponentVersion
    implements Iterable<AbstractVersionComponent>, Version {

  private AbstractVersionComponent head;

  private AbstractVersionComponent last;

  public ComponentVersion() {
    super();
  }

  @Override
  public Iterator<AbstractVersionComponent> iterator() {
    return new VersionComponentIterator(head);
  }

  // @Override
  // public int hashCode() {
  // final int prime = 31;
  // int result = 1;
  // result = prime * result + ((components == null) ? 0 : components.hashCode());
  // return result;
  // }
  //
  // @Override
  // public boolean equals(Object obj) {
  // if (this == obj)
  // return true;
  // if (obj == null)
  // return false;
  // if (getClass() != obj.getClass())
  // return false;
  // ComponentVersion other = (ComponentVersion) obj;
  // if (components == null) {
  // if (other.components != null)
  // return false;
  // }
  // else
  // if (!components.equals(other.components))
  // return false;
  // return true;
  // }
  //
  // @Override
  // public String toString() {
  // StringBuilder b = new StringBuilder();
  // Iterator<AbstractVersionComponent> i = components.iterator();
  // b.append(i.next());
  // while (i.hasNext()) {
  // b.append('.').append(i.next());
  // }
  // return b.toString();
  // }
  //
  @Override
  public int compareTo(Version o) {
    if (this == o)
      return 0;

    if (getClass() != o.getClass())
      throw new RuntimeException(getClass() + " != " + o.getClass());

    Iterator<AbstractVersionComponent> other = ((ComponentVersion) o).iterator();
    for (AbstractVersionComponent t : this) {
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

  public AbstractVersionComponent last() {
    return last;
  }

  public void add(AbstractVersionComponent component) {
    if (null == head)
      head = component;

    else
      last.setNext(component);

    last = component;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (AbstractVersionComponent component : this) {
      b.append(component);
    }
    return b.toString();
  }

}
