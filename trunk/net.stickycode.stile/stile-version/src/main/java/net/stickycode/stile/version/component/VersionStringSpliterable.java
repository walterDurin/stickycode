package net.stickycode.stile.version.component;

import java.util.Iterator;

public class VersionStringSpliterable
    implements Iterable<VersionString> {

  private final String source;

  public VersionStringSpliterable(String source) {
    super();
    this.source = source;
  }

  @Override
  public Iterator<VersionString> iterator() {
    return new VersionStringSpliterator(source);
  }

}
