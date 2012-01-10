package net.stickycode.resource.directory;

import java.io.File;
import java.util.Iterator;

import net.stickycode.resource.ResourceReference;
import net.stickycode.resource.ResourceReferences;

public class FilesFromResources
    implements Iterable<File> {

  public class FileIterator
      implements Iterator<File> {

    private final Iterator<ResourceReference> delegate;

    public FileIterator(Iterator<ResourceReference> delegate) {
      super();
      this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
      return delegate.hasNext();
    }

    @Override
    public File next() {
      return delegate.next().toFile();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Cannot remove files from the underlying this as this iteration is immutable");
    }
  }

  private final ResourceReferences resources;

  public FilesFromResources(ResourceReferences resources) {
    super();
    this.resources = resources;
  }

  @Override
  public Iterator<File> iterator() {
    return new FileIterator(resources.iterator());
  }

}
