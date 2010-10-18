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
package net.stickycode.example.mockwire.helloworld;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.stickycode.mockwire.Bless;
import net.stickycode.mockwire.Mock;
import net.stickycode.mockwire.junit4.MockwireRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockwireRunner.class)
public class UnitDependenciesTest {

  public interface Dependency {

    void call();
  }

  public static class Unit {

    @Inject
    private Dependency dependency;

    public void call() {
      dependency.call();
    }
  }

  @Inject
  Dependency dip;


  @Bless
  Unit unit;

  @Mock
  Dependency mocked;

  @Test
  public void simple() {
    assertThat(unit).isNotNull();
    assertThat(mocked).isNotNull();
    assertThat(dip).isNotNull();
    assertThat(unit.dependency).isNotNull();

    unit.call();
    verify(mocked).call();
  }
}
