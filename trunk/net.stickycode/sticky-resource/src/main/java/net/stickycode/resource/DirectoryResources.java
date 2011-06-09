package net.stickycode.resource;

import static net.stickycode.exception.Preconditions.notNull;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DirectoryResources
    extends Resources {

  private final class DirectoryOrExtensionFilter
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

      return resourceType.matchesExtension(path.getName());
    }
  }

  private final List<Resource> resources = new ArrayList<Resource>();

  private final File baseDirectory;

  private final ResourceType resourceType;

  public DirectoryResources(File baseDirectory, ResourceType resourceType) {
    this.baseDirectory = notNull(baseDirectory, "Base directory cannot be null");
    this.resourceType = notNull(resourceType, "The resource type cannot be null");
    scan(this.baseDirectory);
  }

  private void scan(File directory) {
    for (File path : directory.listFiles(new DirectoryOrExtensionFilter(resourceType))) {
      if (path.isFile())
        resources.add(new FileResource(path));
      else
        scan(path);
    }
  }

  @Override
  public Iterator<Resource> iterator() {
    return resources.iterator();
  }

}
