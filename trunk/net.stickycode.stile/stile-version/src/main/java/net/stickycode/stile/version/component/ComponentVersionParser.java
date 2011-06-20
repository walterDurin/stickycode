package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notBlank;

import java.util.LinkedList;
import java.util.List;

import net.stickycode.stile.version.VersionParser;


public class ComponentVersionParser implements VersionParser {

  @Override
  public ComponentVersion parse(String versionString) {
    return process(notBlank(versionString, "Version spec cannot be blank"));
  }

  private ComponentVersion process(String versionString) {
    List<AbstractVersionComponent> v = new LinkedList<AbstractVersionComponent>();

    VersionString previous = null;
    for (VersionString s : new VersionStringSpliterable(versionString)) {
      if (s.isNumber())
        v.add(parseNumeric(previous, s));

      else if (s.isString())
        v.add(parseString(s));
    }

    return new ComponentVersion(v);
  }

  private AbstractVersionComponent parseString(VersionString s) {
    try {
      return new DefinedStringVersionComponent(VersionDefinition.fromCode(s.toString()));
    }
    catch (IllegalArgumentException e) {
      return new StringVersionComponent(s.toString());
    }
  }

  private AbstractVersionComponent parseNumeric(VersionString previous, VersionString s) {
//    if (s.startsWith("p"))
//      return new PatchNumericVersionComponent(s.substring(1));
//
//    if (s.startsWith("r"))
//      return new RevisionNumericVersionComponent(s.substring(1));

    return new NumericVersionComponent(Integer.valueOf(s.toString()));
  }

}
