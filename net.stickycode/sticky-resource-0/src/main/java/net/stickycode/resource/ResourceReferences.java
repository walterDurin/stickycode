package net.stickycode.resource;

import java.util.Iterator;

abstract public class ResourceReferences
    implements Iterable<ResourceReference> {

  @Override
  abstract public Iterator<ResourceReference> iterator();

  abstract public String getReference();

}
