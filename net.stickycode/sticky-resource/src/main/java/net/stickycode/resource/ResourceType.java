package net.stickycode.resource;


public class ResourceType {

  private final String suffix;

  public ResourceType(String suffix) {
    super();
    this.suffix = suffix;
  }

  public boolean matchesExtension(String fileName) {
    return fileName.endsWith(suffix);
  }

}
