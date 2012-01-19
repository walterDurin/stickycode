package net.stickycode.resource;

public class ResourceSpecification {

  private String path;

  private String protocol;

  private String type;

  /**
   * The context the resource is being used in. Relevant mostly for classloading
   */
  private Class<?> context;

  public ResourceSpecification(Class<?> context, String value) {
    if (null == context)
      throw new NullPointerException("Resource specificiations must have a context");

    this.context = context;
    
    if (null == value)
      throw new NullPointerException("Resource specifications cannot be null");

    if (value.trim().length() == 0)
      throw new IllegalArgumentException(
          "Resource specifications cannot be blank, the simplest form is 'x' which means ::classpath://x");

    int endOfProtocol = value.indexOf("://");
    if (endOfProtocol == -1)
      defaultToClasspath(value);
    else
      processComponents(value.substring(0, endOfProtocol), value.substring(endOfProtocol + 3));
  }

  private void processComponents(String scheme, String path) {
    this.path = path;
    String[] components = scheme.split(":");
    if (components.length == 1)
      this.protocol = scheme;
    else
      this.protocol = components[2];
  }

  private void defaultToClasspath(String value) {
    this.protocol = "classpath";
    if (value.startsWith("::"))
      this.path = value.substring(2);
    else
      this.path = value;
  }

  public String getProtocol() {
    return protocol;
  }

  public String getPath() {
    return path;
  }

  public Class<?> getContext() {
    return context;
  }

}
