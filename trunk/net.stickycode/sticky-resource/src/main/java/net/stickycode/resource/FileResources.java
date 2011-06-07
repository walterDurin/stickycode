package net.stickycode.resource;

import java.io.File;
import java.util.Iterator;

public class FileResources
    implements Iterable<File> {

  public class FileIterator
      implements Iterator<File> {

    private final Iterator<Resource> delegate;

    public FileIterator(Iterator<Resource> delegate) {
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

  private final Resources resources;

  public FileResources(Resources resources) {
    super();
    this.resources = resources;
  }

  @Override
  public Iterator<File> iterator() {
    return new FileIterator(resources.iterator());
  }

}
