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

import net.stickycode.stile.version.Bound;
import net.stickycode.stile.version.Version;
import net.stickycode.stile.version.VersionRange;

public class ComponentVersionRange
    implements VersionRange {

  private final Bound lowerBound;

  private final Bound upperBound;

  public ComponentVersionRange(Bound lowerBound, Bound upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean includes(Version version) {
    return false;
  }

  @Override
  public VersionRange intersection(VersionRange range) {
    return null;
  }

  public Bound getLowerBound() {
    return lowerBound;
  }

  public Bound getUpperBound() {
    return upperBound;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    if (lowerBound.isExclusive())
      b.append("(");
    else
      b.append("[");
    
    b.append(lowerBound.getVersion()).append(",").append(upperBound.getVersion());
    
    if (upperBound.isExclusive())
      b.append(")");
    else
      b.append("]");
    
    return b.toString();
  }

}
