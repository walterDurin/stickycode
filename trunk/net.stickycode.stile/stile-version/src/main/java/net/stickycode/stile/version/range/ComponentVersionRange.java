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

import net.stickycode.stile.version.Version;
import net.stickycode.stile.version.VersionRange;


public class ComponentVersionRange
    implements VersionRange {

  public ComponentVersionRange(Bound lowerBound, Bound upperBound) {
  }

  @Override
  public boolean includes(Version version) {
    return false;
  }

  @Override
  public VersionRange intersection(VersionRange range) {
    return null;
  }

}
