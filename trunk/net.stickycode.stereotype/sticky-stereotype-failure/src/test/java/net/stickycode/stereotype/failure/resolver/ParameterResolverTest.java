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
package net.stickycode.stereotype.failure.resolver;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.junit.Test;

import net.stickycode.stereotype.failure.BlankParameterFailure;
import net.stickycode.stereotype.failure.InvalidParameterFailure;
import net.stickycode.stereotype.failure.NullParameterFailure;
import net.stickycode.stereotype.failure.resolver.ParameterResolver;


public class ParameterResolverTest {

  @Test(expected=NullPointerException.class)
  public void nullMessageExcepts() {
    ParameterResolver.resolve(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void emptyMessageExcepts() {
    ParameterResolver.resolve("");
  }

  @Test(expected=TooManyArgumentsException.class)
  public void noPlaceHolderForArgumentExcepts() {
    ParameterResolver.resolve("a message with no placeholders", "a");
  }

  @Test(expected=ThrowablesCannotBeParametersException.class)
  public void throwableArgumentsExceptAsItsACommonMistake() {
    ParameterResolver.resolve("placeholder and throwable argument {}", new RuntimeException());
  }

  @Test
  public void onePlaceHolderOneArgument() {
    assertThat(ParameterResolver.resolve("{}", "a")).isEqualTo("a");
  }

  @Test
  public void twoPlaceHolderTwoArgument() {
    assertThat(ParameterResolver.resolve("{}{}", "a", "b")).isEqualTo("ab");
    assertThat(ParameterResolver.resolve("z{}z{}z", "a", "b")).isEqualTo("zazbz");
  }

  @Test
  public void checkThatPartialPlaceHoldersDontCauseIssues() {
    assertThat(ParameterResolver.resolve("z} {} {z{}z", "a", "b")).isEqualTo("z} a {zbz");
    assertThat(ParameterResolver.resolve("}{")).isEqualTo("}{");
    assertThat(ParameterResolver.resolve("z} \\{\\} {z{}z", "a")).isEqualTo("z} \\{\\} {zaz");
  }

  @Test(expected=NullPointerException.class)
  public void checkThatNullParametersExcept() {
    ParameterResolver.resolve("{}", (Object[])null);
  }

  @Test(expected=NullPointerException.class)
  public void checkThatANullParameterExcepts() {
    ParameterResolver.resolve("{}", new Object[] {null});
  }

  @Test(expected=TooManyArgumentsException.class)
  public void tooManyArguments() {
    ParameterResolver.resolve("{}", "a", "b");
  }

  @Test(expected=TooManyPlaceHoldersException.class)
  public void tooManyPlaceHolders() {
    ParameterResolver.resolve("{} {}", "b");
  }

}
