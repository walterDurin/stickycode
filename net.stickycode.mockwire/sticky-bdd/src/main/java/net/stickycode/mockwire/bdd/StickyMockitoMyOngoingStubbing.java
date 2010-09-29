package net.stickycode.mockwire.bdd;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

/**
 * See original {@link OngoingStubbing}
 */
public interface StickyMockitoMyOngoingStubbing<T> {

    /**
     * See original {@link OngoingStubbing#thenAnswer(Answer)}
     */
    StickyMockitoMyOngoingStubbing<T> willAnswer(Answer<?> answer);

    /**
     * See original {@link OngoingStubbing#thenReturn(Object)}
     */
    StickyMockitoMyOngoingStubbing<T> willReturn(T value);

    /**
     * See original {@link OngoingStubbing#thenReturn(Object, Object...)}
     */
    StickyMockitoMyOngoingStubbing<T> willReturn(T value, T... values);

    /**
     * See original {@link OngoingStubbing#thenThrow(Throwable...)}
     */
    StickyMockitoMyOngoingStubbing<T> willThrow(Throwable... throwables);

    /**
     * See original {@link OngoingStubbing#thenCallRealMethod()}
     */
    StickyMockitoMyOngoingStubbing<T> willCallRealMethod();

}