/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
package net.stickycode.mockwire.contained;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.stickycode.stereotype.Configured;

public class PostConstructed {
  @Configured
  String value;

  boolean initialised = false;
  boolean destroyed = false;

  @PostConstruct
  public void init() {
    if (value == null)
      throw new RuntimeException();

    initialised = true;
  }

  @PreDestroy
  public void destroy() {
    destroyed = true;
  }
}