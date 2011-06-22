package net.stickycode.stile.version.component;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class InvalidVersionStringException
    extends PermanentException {

  public InvalidVersionStringException(CharSequence source, int start) {
    super("Character '{}' is not valid in version specification '{}'. ", source, source.charAt(start));
  }

}
