package net.stickycode.stile.version;

import org.fest.assertions.AssertExtension;
import org.fest.assertions.ComparableAssert;


public class VersionAssert
    extends ComparableAssert<VersionAssert, Version> 
    implements AssertExtension {

  protected VersionAssert(Class<VersionAssert> selfType, Version actual) {
    super(selfType, actual);
  }

}
