package net.stickycode.bootstrap.metadata;

import java.util.ArrayList;
import java.util.List;

public class ComponentDefinition {

  private String name;

  private List<String> stereotype;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComponentDefinition other = (ComponentDefinition) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    }
    else
      if (!name.equals(other.name))
        return false;
    return true;
  }

  public void stereotype(String desc) {
    stereotype().add(desc);
  }

  private List<String> stereotype() {
    if (this.stereotype == null)
      this.stereotype = new ArrayList<>();

    return this.stereotype;
  }

  public String getName() {
    return name;
  }

  public boolean hasSteretype() {
    return stereotype != null;
  }

  public ComponentDefinition withName(String string) {
    this.name = string;
    return this;
  }

  @Override
  public String toString() {
    return name;
  }
}
