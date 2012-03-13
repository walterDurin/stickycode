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

public enum ComponentOrdering {

  Snapshot("snapshot"),
  Alpha("alpha"),
  Beta("beta"),
  Gamma("gamma"),
  ReleaseCandidate("rc"),
  Release(),
  FinalCandidateSelection("fcs", "ga"),
  Revision("r"),
  Patch("p", "sec"),
  ;

  private String[] names;

  private ComponentOrdering(String... names) {
    this.names = names;
  }

  public static ComponentOrdering fromCode(String code) {
    for (ComponentOrdering ordering : values()) {
      for (String name : ordering.names) {
        if (name.equals(code.toLowerCase()))
          return ordering;
      }
    }

    return null;
  }
}
