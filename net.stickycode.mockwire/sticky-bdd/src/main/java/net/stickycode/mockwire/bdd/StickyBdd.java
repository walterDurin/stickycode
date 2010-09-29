/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 * http://www.oensource.org/licenses/mit-license.php
 */

package net.stickycode.mockwire.bdd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fest.assertions.AssertExtension;
import org.fest.assertions.Assertions;
import org.fest.assertions.BigDecimalAssert;
import org.fest.assertions.BooleanArrayAssert;
import org.fest.assertions.BooleanAssert;
import org.fest.assertions.ByteArrayAssert;
import org.fest.assertions.ByteAssert;
import org.fest.assertions.CharArrayAssert;
import org.fest.assertions.CharAssert;
import org.fest.assertions.CollectionAssert;
import org.fest.assertions.DoubleArrayAssert;
import org.fest.assertions.DoubleAssert;
import org.fest.assertions.FileAssert;
import org.fest.assertions.FloatArrayAssert;
import org.fest.assertions.FloatAssert;
import org.fest.assertions.ImageAssert;
import org.fest.assertions.IntArrayAssert;
import org.fest.assertions.IntAssert;
import org.fest.assertions.ListAssert;
import org.fest.assertions.LongArrayAssert;
import org.fest.assertions.LongAssert;
import org.fest.assertions.MapAssert;
import org.fest.assertions.ObjectArrayAssert;
import org.fest.assertions.ObjectAssert;
import org.fest.assertions.ShortArrayAssert;
import org.fest.assertions.ShortAssert;
import org.fest.assertions.StringAssert;
import org.fest.assertions.ThrowableAssert;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;


public class StickyBdd {

  /**
   * see original {@link Mockito#when(Object)}
   */
  public static <T> StickyMockitoMyOngoingStubbing<T> given(T methodCall) {
    return new StickyMockitoOngoingStubbingImpl<T>(Mockito.when(methodCall));
  }

  /**
   * see original {@link Mockito#doThrow(Throwable)}
   */
  public static StickyMockitoStubber willThrow(Throwable toBeThrown) {
    return new StickyMockitoStubberImpl(Mockito.doThrow(toBeThrown));
  }

  /**
   * see original {@link Mockito#doAnswer(Answer)}
   */
  public static StickyMockitoStubber willAnswer(Answer answer) {
    return new StickyMockitoStubberImpl(Mockito.doAnswer(answer));
  }

  /**
   * see original {@link Mockito#doNothing()}
   */
  public static StickyMockitoStubber willDoNothing() {
    return new StickyMockitoStubberImpl(Mockito.doNothing());
  }

  /**
   * see original {@link Mockito#doReturn(Object)}
   */
  public static StickyMockitoStubber willReturn(Object toBeReturned) {
    return new StickyMockitoStubberImpl(Mockito.doReturn(toBeReturned));
  }

  /**
   * see original {@link Mockito#doCallRealMethod()}
   */
  public static StickyMockitoStubber willCallRealMethod() {
    return new StickyMockitoStubberImpl(Mockito.doCallRealMethod());
  }


  private static <T> Collection<T> asCollection(Iterator<T> iterator) {
    List<T> list = new ArrayList<T>();
    while (iterator.hasNext()) list.add(iterator.next());
    return list;
  }

  /**
   * Creates a new instance of <code>{@link BigDecimalAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BigDecimalAssert then(BigDecimal actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link BooleanAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BooleanAssert then(boolean actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link BooleanAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BooleanAssert then(Boolean actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link BooleanArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static BooleanArrayAssert then(boolean[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ImageAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ImageAssert then(BufferedImage actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ByteAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ByteAssert then(byte actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ByteAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ByteAssert then(Byte actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ByteArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ByteArrayAssert then(byte[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link CharAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CharAssert then(char actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link CharAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CharAssert then(Character actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link CharArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CharArrayAssert then(char[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link CollectionAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static CollectionAssert then(Collection<?> actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ListAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   * @since 1.1
   */
  public static ListAssert then(List<?> actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link DoubleAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static DoubleAssert then(double actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link DoubleAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static DoubleAssert then(Double actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link DoubleArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static DoubleArrayAssert then(double[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link FileAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FileAssert then(File actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link FloatAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FloatAssert then(float actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link FloatAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FloatAssert then(Float actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link FloatArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static FloatArrayAssert then(float[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link IntAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static IntAssert then(int actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link IntAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static IntAssert then(Integer actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link IntArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static IntArrayAssert then(int[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link CollectionAssert}</code>.
   * @param actual the value an <code>Iterator</code> that which contents will be added to a new <code>Collection</code>.
   * @return the created assertion object.
   */
  public static CollectionAssert then(Iterator<?> actual) {
    return then(asCollection(actual));
  }

  /**
   * Creates a new instance of <code>{@link LongAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static LongAssert then(long actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link LongAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static LongAssert then(Long actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link LongArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static LongArrayAssert then(long[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link MapAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static MapAssert then(Map<?, ?> actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ObjectAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ObjectAssert then(Object actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ObjectArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ObjectArrayAssert then(Object[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ShortAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ShortAssert then(short actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ShortAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ShortAssert then(Short actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link ShortArrayAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ShortArrayAssert then(short[] actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Creates a new instance of <code>{@link StringAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static StringAssert then(String actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Returns the given assertion. This method improves code readability by surrounding the given assertion with "<code>then</code>".
   * <p>
   * For example, let's assume we have the following custom assertion class:
   *
   * <pre>
   * public class ServerSocketAssertion implements AssertExtension {
   *   private final ServerSocket socket;
   *
   *   public ServerSocketAssertion(ServerSocket socket) {
   *     this.socket = socket;
   *   }
   *
   *   public ServerSocketAssert isConnectedTo(int port) {
   *     then(socket.isBound()).isTrue();
   *     then(socket.getLocalPort()).isEqualTo(port);
   *     then(socket.isClosed()).isFalse();
   *     return this;
   *   }
   * }
   * </pre>
   * </p>
   * <p>
   * We can wrap that assertion with "<code>then</code>" to improve test code readability.
   * <pre>
   *   ServerSocketAssertion socket = new ServerSocketAssertion(server.getSocket());
   *   then(socket).isConnectedTo(2000);
   * </pre>
   * </p>
   *
   * @param <T> the generic type of the user-defined assertion.
   * @param assertion the assertion to return.
   * @return the given assertion.
   */
  public static <T extends AssertExtension> T then(T assertion) {
    return assertion;
  }

  /**
   * Creates a new instance of <code>{@link ThrowableAssert}</code>.
   * @param actual the value to be the target of the assertions methods.
   * @return the created assertion object.
   */
  public static ThrowableAssert then(Throwable actual) {
    return Assertions.assertThat(actual);
  }

  /**
   * Just an identity method so it reads nice
   */
  public static <T> T when(T t) {
    return t;
  }

}
