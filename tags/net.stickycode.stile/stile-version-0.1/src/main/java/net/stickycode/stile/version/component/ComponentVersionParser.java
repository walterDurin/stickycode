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

      else
        if (v.last() == null)
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
