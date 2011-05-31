package net.stickycode.stile.version.component;

class PatchNumericVersionComponent
    extends NumericVersionComponent {

  public PatchNumericVersionComponent(Integer i) {
    super(i);
  }

  public PatchNumericVersionComponent(String s) {
    this(new Integer(s));
  }

}
