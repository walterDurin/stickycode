package net.stickycode.stile.version.component;

class RevisionNumericVersionComponent
    extends NumericVersionComponent {

  public RevisionNumericVersionComponent(Integer i) {
    super(i);
  }

  public RevisionNumericVersionComponent(String s) {
    this(new Integer(s));
  }

}
