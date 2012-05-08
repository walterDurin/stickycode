package net.stickycode.stereotype.resource;

import net.stickycode.stereotype.ConfiguredComponent;

/**
 * Interface used to mark of point where an external resource should be injected.
 * 
 * <pre>
 * &#064;Configured
 * private Resource<String> helpText;
 * 
 * &#064;Configured
 * private Resource<Properties> decodeMappings;
 * 
 * </pre>
 */
@ConfiguredComponent
@Deprecated
public interface Resource<T> {

  /**
   * Return the current value of the resource, depending on the nature of the resource
   * this could be a fixed value or change on every call.
   * 
   * <h2>e.g.</h2>
   * <ul>
   * <li>A string from the classpath might be fixed for the lifetime of the application.</li>
   * <li>A Properties resource loaded from a file might change each time its updated</li>
   * </ul>
   */
  T get();

}
