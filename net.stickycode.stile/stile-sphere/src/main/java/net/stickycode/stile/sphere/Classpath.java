/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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
package net.stickycode.stile.sphere;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.stickycode.stile.artifact.Artifact;


public class Classpath
  implements Iterable<Artifact> {

  private List<Artifact> artifacts = new LinkedList<Artifact>();

  @Override
  public Iterator<Artifact> iterator() {
    return artifacts.iterator();
  }

  public void add(Artifact artifact) {
    artifacts.add(artifact);
  }

}
