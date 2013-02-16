package net.stickycode.stereotype.primitive;

import net.stickycode.exception.TransientException;

@SuppressWarnings("serial")
public class ReadOnlyFileIsModifiableException
    extends TransientException {

  public ReadOnlyFileIsModifiableException(String path) {
    super("Path '' is modifiable but its not supposed to be", path);
  }

}
