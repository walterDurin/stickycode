package net.stickycode.stile.version.component;

import static net.stickycode.exception.Preconditions.notBlank;
import static net.stickycode.exception.Preconditions.notNull;

class RevisionNumericVersionComponent
    extends NumericVersionComponent {

  public RevisionNumericVersionComponent(Integer i) {
    super(notNull(i, "Numeric version cannot be null"));
  }

  public RevisionNumericVersionComponent(String s) {
    this(new Integer(notBlank(s, "Numeric version cannot be blank")));
  }
  
  @Override
  public ComponentOrdering getOrdering() {
    return ComponentOrdering.Revision;
  }

}
