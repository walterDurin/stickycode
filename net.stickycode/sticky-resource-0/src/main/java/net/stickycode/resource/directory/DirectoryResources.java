package net.stickycode.resource.directory;

import static net.stickycode.exception.Preconditions.notNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.stickycode.resource.ResourceReference;
import net.stickycode.resource.ResourceType;
import net.stickycode.resource.ResourceReferences;

public class DirectoryResources
    extends ResourceReferences {

  private final List<ResourceReference> resources = new ArrayList<ResourceReference>();

  private final File baseDirectory;

  private final ResourceType resourceType;

  public DirectoryResources(File baseDirectory, ResourceType resourceType) {
    this.baseDirectory = notNull(baseDirectory, "Base directory cannot be null");
    this.resourceType = notNull(resourceType, "The resource type cannot be null");
    // TODO create a stateful scanner class that uses iteration not recursion
    if (!this.baseDirectory.isDirectory())
      throw new RuntimeException(baseDirectory.getAbsolutePath() + " is not a directory");

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
  public Iterator<ResourceReference> iterator() {
    return resources.iterator();
  }

  @Override
  public String getReference() {
    return null;
  }

}
