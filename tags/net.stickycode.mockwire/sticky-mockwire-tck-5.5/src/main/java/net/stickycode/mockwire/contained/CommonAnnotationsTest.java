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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockwireRunner.class)
@MockwireConfigured("beanWithLifecycle.value=something")
public class CommonAnnotationsTest {

  @UnderTest
  BeanWithLifecycle target;

  private static BeanWithLifecycle preserve;

  @Before
  public void before() {
    assertThat(target).isNull();
  }

  @Test
  public void cycle() {
    assertThat(target).isNotNull();
    assertThat(target.initialised).isTrue();
    assertThat(target.destroyed).isFalse();
    assertThat(target.value).isEqualTo("something");
    preserve = target;
  }

  @After
  public void after() {
    assertThat(target.destroyed).isFalse();
  }

  @AfterClass
  public static void afterClass() {
    assertThat(preserve.destroyed).isTrue();
  }
}
