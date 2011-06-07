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

    @Override
    public boolean accept(File path) {
      if (path.isDirectory())
        return path.canRead();

      return path.getName().endsWith(".java");
    }
  }

  private final List<Resource> resources = new ArrayList<Resource>();
  private final File baseDirectory;

  public DirectoryResources(File baseDirectory) {
    this.baseDirectory = notNull(baseDirectory, "Base directory cannot be null");
    scan(baseDirectory);
  }

  private void scan(File directory) {
    for (File path: directory.listFiles(new DirectoryOrExtensionFilter())) {
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
