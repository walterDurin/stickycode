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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

public class StickyMockitoOngoingStubbingImpl<T>
    implements StickyMockitoMyOngoingStubbing<T> {

  private final OngoingStubbing<T> mockitoOngoingStubbing;

  public StickyMockitoOngoingStubbingImpl(OngoingStubbing<T> ongoingStubbing) {
    this.mockitoOngoingStubbing = ongoingStubbing;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoMyOngoingStubbing#willAnswer(org.mockito.stubbing.Answer)
   */
  public StickyMockitoMyOngoingStubbing<T> willAnswer(Answer<?> answer) {
    return new StickyMockitoOngoingStubbingImpl<T>(mockitoOngoingStubbing.thenAnswer(answer));
  }

  /*
   * (non-Javadoc)
   *
   * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoMyOngoingStubbing#willReturn(java.lang.Object)
   */
  public StickyMockitoMyOngoingStubbing<T> willReturn(T value) {
    return new StickyMockitoOngoingStubbingImpl<T>(mockitoOngoingStubbing.thenReturn(value));
  }

  /*
   * (non-Javadoc)
   *
   * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoMyOngoingStubbing#willReturn(java.lang.Object, T[])
   */
  public StickyMockitoMyOngoingStubbing<T> willReturn(T value, T... values) {
    return new StickyMockitoOngoingStubbingImpl<T>(mockitoOngoingStubbing.thenReturn(value, values));
  }

  /*
   * (non-Javadoc)
   *
   * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoMyOngoingStubbing#willThrow(java.lang.Throwable[])
   */
  public StickyMockitoMyOngoingStubbing<T> willThrow(Throwable... throwables) {
    return new StickyMockitoOngoingStubbingImpl<T>(mockitoOngoingStubbing.thenThrow(throwables));
  }

  public StickyMockitoMyOngoingStubbing<T> willCallRealMethod() {
    return new StickyMockitoOngoingStubbingImpl<T>(mockitoOngoingStubbing.thenCallRealMethod());
  }

}
