/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notNull;
import net.stickycode.util.Linked;

public abstract class AbstractVersionComponent
    implements Comparable<AbstractVersionComponent>, Linked<AbstractVersionComponent> {

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


    return getOrdering().compareTo(o.getOrdering());
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

  public AbstractVersionComponent getNext() {
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

  public String fullString() {
    if (qualifier != null)
      return versionString.fullString() + qualifier.fullString();

    return versionString.fullString();
  }

  public void qualify(ComponentOrdering ordering, CharacterVersionString qualifier) {
    this.qualifier = qualifier;
    this.ordering = ordering;
  }
}
