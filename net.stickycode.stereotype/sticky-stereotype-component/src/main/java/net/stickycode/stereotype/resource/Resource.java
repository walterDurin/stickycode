package net.stickycode.stereotype.resource;

import net.stickycode.stereotype.ConfiguredComponent;

/**
 * Deprecated as the Resource stereotype is a separate project to separate concerns.
 * 
 * <pre>
 * &lt;dependency&gt;
 * &lt;groupId&gt;net.stickycode.resource&lt;/groupId&gt;
 * &lt;artifactId&gt;sticky-resource-stereotype&lt;/artifactId&gt;
 * &lt;version&gt;[1,2)&lt;/version&gt;
 * &lt;/dependency&gt;
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
