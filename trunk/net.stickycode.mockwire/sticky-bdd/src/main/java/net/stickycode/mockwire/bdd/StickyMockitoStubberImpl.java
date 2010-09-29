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

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

public class StickyMockitoStubberImpl implements StickyMockitoStubber {

    private final Stubber mockitoStubber;

    public StickyMockitoStubberImpl(Stubber mockitoStubber) {
        this.mockitoStubber = mockitoStubber;
    }

    /* (non-Javadoc)
     * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoStubber#given(java.lang.Object)
     */
    public <T> T given(T mock) {
        return mockitoStubber.when(mock);
    }

    /* (non-Javadoc)
     * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoStubber#willAnswer(org.mockito.stubbing.Answer)
     */
    public StickyMockitoStubber willAnswer(Answer answer) {
        return new StickyMockitoStubberImpl(mockitoStubber.doAnswer(answer));
    }

    /* (non-Javadoc)
     * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoStubber#willNothing()
     */
    public StickyMockitoStubber willDoNothing() {
        return new StickyMockitoStubberImpl(mockitoStubber.doNothing());
    }

    /* (non-Javadoc)
     * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoStubber#willReturn(java.lang.Object)
     */
    public StickyMockitoStubber willReturn(Object toBeReturned) {
        return new StickyMockitoStubberImpl(mockitoStubber.doReturn(toBeReturned));
    }

    /* (non-Javadoc)
     * @see org.mockitousage.customization.StickyMockitoMockito.StickyMockitoStubber#willThrow(java.lang.Throwable)
     */
    public StickyMockitoStubber willThrow(Throwable toBeThrown) {
        return new StickyMockitoStubberImpl(mockitoStubber.doThrow(toBeThrown));
    }
}