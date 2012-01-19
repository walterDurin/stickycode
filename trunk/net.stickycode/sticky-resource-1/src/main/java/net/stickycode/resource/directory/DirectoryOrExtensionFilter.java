package net.stickycode.resource.directory;

import java.io.File;
import java.io.FileFilter;

import net.stickycode.resource.ResourceType;

final class DirectoryOrExtensionFilter
    implements FileFilter {

  private final ResourceType resourceType;

  public DirectoryOrExtensionFilter(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  @Override
  public boolean accept(File path) {
    if (!path.canRead())
      return false;

    if (path.isDirectory())
      return true;

    for (String extensions : resourceType.getExtensions()) {
      if (path.getName().endsWith(extensions))
        return true;
    }

    return false;
  }
}