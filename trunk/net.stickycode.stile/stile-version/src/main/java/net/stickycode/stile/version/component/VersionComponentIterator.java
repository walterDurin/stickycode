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

import static net.stickycode.exception.Preconditions.notNull;

import java.util.Iterator;

public class VersionComponentIterator
    implements Iterator<AbstractVersionComponent> {

  private AbstractVersionComponent current;

  public VersionComponentIterator(AbstractVersionComponent head) {
    this.current = notNull(head, "First component cannot be null");
  }

  @Override
  public boolean hasNext() {
    return current != null;
  }

  @Override
  public AbstractVersionComponent next() {
    AbstractVersionComponent was = current;
    current = current.getNext();
    return was;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Version component iterator is immutable");
  }

}
