package net.stickycode.bootstrap.metadata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ComponentRegister
    implements Iterable<ComponentDefinition> {

  private Map<String, ComponentDefinition> components = new HashMap<>();

  private Map<String, Class<?>> realised = new HashMap<>();

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

  public void realise() {
    for (String component : components.keySet()) {
      try {
        realised.put(component, Thread.currentThread().getContextClassLoader().loadClass(component));
      }
      catch (ClassNotFoundException ignored) {
      }
    }
    Set<String> missing = new HashSet<>(components.keySet());
    missing.removeAll(realised.keySet());
    if (!missing.isEmpty())
      throw new FailureToRealiseSterotypes(missing);
  }

}
