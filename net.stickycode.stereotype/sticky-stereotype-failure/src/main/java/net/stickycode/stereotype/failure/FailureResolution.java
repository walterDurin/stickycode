package net.stickycode.stereotype.failure;

/**
 * A representation of how to resolve failures
 */
public enum FailureResolution {
  /**
   * The failure will persist on retry, and will most likely require development intervention to resolve.
   */
  Application,

  /**
   * The error scenario is not permanent and retrying the request will succeed but may require
   * environmental intervention.
   * 
   * For example a missing resource, or failing network connection are good examples
   */
  Environment,

  /**
   * The error is related to data, and retrying will require that the relevant data is corrected.
   * 
   * As example would be missing elements e.g. a resource not being found for an id
   */
  Data,

  /**
   * The error scenario is not permanent and retrying the same request will eventually succeed.
   * 
   * For example resource contention would be a good example of this failure.
   */
  Nothing
}
