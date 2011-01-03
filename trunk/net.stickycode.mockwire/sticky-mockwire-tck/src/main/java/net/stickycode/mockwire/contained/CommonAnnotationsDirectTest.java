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

import org.junit.Test;

import net.stickycode.mockwire.Mockwire;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.stereotype.Configured;

import static org.fest.assertions.Assertions.assertThat;

@MockwireConfigured("postConstructed.value=something")
public class CommonAnnotationsDirectTest {


  public static class PostConstructed {
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

  @UnderTest
  PostConstructed target;

  @Test
  public void postConstruct() {
    Mockwire.isolate(this);
    assertThat(target.initialised).isTrue();
    assertThat(target.value).isEqualTo("something");
    assertThat(target.destroyed).isTrue();
  }
}
