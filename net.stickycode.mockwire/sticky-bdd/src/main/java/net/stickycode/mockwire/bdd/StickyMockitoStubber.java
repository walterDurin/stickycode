package net.stickycode.mockwire.bdd;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

/**
 * See original {@link Stubber}
 */
public interface StickyMockitoStubber {

  /**
   * See original {@link Stubber#doAnswer(Answer)}
   */
  StickyMockitoStubber willAnswer(Answer answer);

  /**
   * See original {@link Stubber#doNothing()}
   */
  StickyMockitoStubber willDoNothing();

  /**
   * See original {@link Stubber#doReturn(Object)}
   */
  StickyMockitoStubber willReturn(Object toBeReturned);

  /**
   * See original {@link Stubber#doThrow(Throwable)}
   */
  StickyMockitoStubber willThrow(Throwable toBeThrown);

  /**
   * See original {@link Stubber#when(Object)}
   */
  <T> T given(T mock);
}
