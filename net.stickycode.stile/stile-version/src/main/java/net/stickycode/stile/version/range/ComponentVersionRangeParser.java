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
package net.stickycode.stile.version.range;

import static net.stickycode.exception.Preconditions.notBlank;

import javax.inject.Inject;

import net.stickycode.stile.version.Bound;
import net.stickycode.stile.version.VersionRange;
import net.stickycode.stile.version.VersionRangeParser;
import net.stickycode.stile.version.component.ComponentVersionParser;


public class ComponentVersionRangeParser
    implements VersionRangeParser {

  @Inject
  ComponentVersionParser parser;

  @Override
  public VersionRange parseVersionRange(String specification) {
    String versionRange = notBlank(specification, "Version range specification cannot be blank");
    int comma = versionRange.indexOf(',');
    if (comma == -1)
      throw new VersionRangeMissingCommaException(versionRange);

    if (comma < 2 || comma > versionRange.length() - 3)
      throw new VersionRangeMissingBoundException(versionRange);

    Bound lowerBound = parseLowerBound(versionRange, comma);
    Bound upperBound = parseUpperBound(versionRange, comma);

    return new ComponentVersionRange(lowerBound, upperBound);
  }

  private Bound parseUpperBound(String versionRange, int comma) {
    char upperContraint = versionRange.charAt(versionRange.length() - 1);
    if (']' == upperContraint)
      return new InclusiveBound(parser.parse(versionRange.substring(comma + 1, versionRange.length() - 1)));

    if (')' == upperContraint)
      return new ExclusiveBound(parser.parse(versionRange.substring(comma + 1, versionRange.length() - 1)));

    throw new InvalidVersionRangeSpecificationException(versionRange, upperContraint);
  }

  private Bound parseLowerBound(String versionRange, int comma) {
    char lowerConstraint = versionRange.charAt(0);
    if ('[' == lowerConstraint)
      return new InclusiveBound(parser.parse(versionRange.substring(1, comma)));

    if ('(' == lowerConstraint)
      return new ExclusiveBound(parser.parse(versionRange.substring(1, comma)));

    throw new InvalidVersionRangeSpecificationException(lowerConstraint, versionRange);
  }
}
