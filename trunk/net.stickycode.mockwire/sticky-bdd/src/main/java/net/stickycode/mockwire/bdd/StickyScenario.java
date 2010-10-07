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
package net.stickycode.mockwire.bdd;


public class StickyScenario {

  public static class FluentGivenImpl
      implements FluentGiven {

    private String assumption;

    public FluentGivenImpl(String assumption) {
      this.assumption = assumption;
    }

    @Override
    public <T> StickyMockitoMyOngoingStubbing<T> thatIs(T methodCall) {
      return StickyBdd.given(methodCall);
    }

  }

  public static interface FluentGiven {

    <T> StickyMockitoMyOngoingStubbing<T> thatIs(T zipCode);

  }

  public static FluentGiven given(String assumption) {
    return new FluentGivenImpl(assumption);
  }

  public static Object and(String string) {
    return null;
  }

  public static Object when(String string) {
    return null;
  }

}
