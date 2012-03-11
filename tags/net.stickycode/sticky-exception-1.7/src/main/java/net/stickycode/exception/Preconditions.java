package net.stickycode.exception;

/**
 * Good error handling involves validation as well, provide a nice means of validating inputs.
 */
public abstract class Preconditions {

  public static <T> T notNull(T i, String message) {
    if (i == null)
      throw new NullPointerException(message);

    return i;
  }

  public static String notBlank(String i, String message) {
    return length(message, notNull(i, message), 0);
  }

  public static String length(String message, String i, int length) {
    if (i.trim().length() == length)
      throw new IllegalArgumentException(message);

    return i;
  }

}
