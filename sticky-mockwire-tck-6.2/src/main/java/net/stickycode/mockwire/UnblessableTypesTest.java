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
package net.stickycode.mockwire;

import org.junit.Test;

public class UnblessableTypesTest {

  private class NonStaticType {}
  private class NonStaticTypeTest {
    @SuppressWarnings("unused")
    @UnderTest NonStaticType hidden;
  }

  @Test(expected=NonStaticMemberTypesCannotBePutUnderTestException.class)
  public void checkBlessedStaticInnerTypesError() {
    // invoke statically so we can assert the exception is expected
    Mockwire.isolate(new NonStaticTypeTest());
  }

  private interface Super {}
  private class CantBlessInterfacesTest {
    @SuppressWarnings("unused")
    @UnderTest Super iface;
  }

  @Test(expected=InterfacesCannotBePutUnderTestException.class)
  public void checkBlessedIntefacesError() {
    // invoke statically so we can assert the exception is expected
    Mockwire.isolate(new CantBlessInterfacesTest());
  }

  private class CantBlessVoidMethodsTest {
    @SuppressWarnings("unused")
    @UnderTest void voidMethod() {};
  }
  @Test(expected=VoidMethodsCannotBeUsedAsFactoriesForCodeUnderTestException.class)
  public void checkBlessVoidMethodsError() {
    Mockwire.isolate(new CantBlessVoidMethodsTest());
  }
}
