package net.stickycode.bootstrap.metadata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ComponentRegister
    implements Iterable<ComponentDefinition> {

  private Map<String, ComponentDefinition> components = new HashMap<>();

  void register(ComponentDefinition definition) {
    this.components.put(definition.getName(), definition);
  }

  @Override
  public Iterator<ComponentDefinition> iterator() {
    return components.values().iterator();
  }

  @Override
  public String toString() {
    return String.format("%s", components.keySet());
  }

}
