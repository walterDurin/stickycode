package net.stickycode.stereotype.configured;

/**
 * <p>
 * A configurable type that will give a a decrypted secret on demand.
 * </p>
 */
public interface ConfiguredSecret {

  /**
   * @return the secret
   */
  String getSecret();

  /**
   * Should always be implemented and returned the configured value not the decrypted value.
   */
  String toString();

}
