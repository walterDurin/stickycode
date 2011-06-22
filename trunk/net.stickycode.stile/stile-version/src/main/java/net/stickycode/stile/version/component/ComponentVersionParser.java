package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notBlank;
import net.stickycode.stile.version.VersionParser;

public class ComponentVersionParser
    implements VersionParser {

  @Override
  public ComponentVersion parse(String versionString) {
    return process(notBlank(versionString, "Version spec cannot be blank"));
  }

  private ComponentVersion process(String versionString) {
    ComponentVersion v = new ComponentVersion();
    for (VersionString s : new VersionStringSpliterable(versionString)) {
      if (s.isNumber())
        v.add(new NumericVersionComponent(s.asNumeric()));

      else if (v.last() == null)
        v.add(new StringVersionComponent(s.asCharacter()));

      else {
        ComponentOrdering ordering = ComponentOrdering.fromCode(s.toString());
        if (ordering == null)
          v.add(new StringVersionComponent(s.asCharacter()));
        else
          v.last().qualify(ordering, s.asCharacter());
      }
    }

    return v;
  }

}
