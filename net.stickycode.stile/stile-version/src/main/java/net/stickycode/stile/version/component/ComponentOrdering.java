package net.stickycode.stile.version.component;

public enum ComponentOrdering {

  Snapshot("snapshot"),
  Gamma("gamma"),
  Beta("beta"),
  Alpha("alpha"),
  ReleaseCandidate("rc"),
  Release(),
  FinalCandidateSelection("fcs", "ga"),
  Revision("r"),
  Patch("p", "sec"),
  ;

  private String[] names;

  private ComponentOrdering(String... names) {
    this.names = names;
  }

  public static ComponentOrdering fromCode(String code) {
    for (ComponentOrdering ordering : values()) {
      for (String name : ordering.names) {
        if (name.equals(code.toLowerCase()))
          return ordering;
      }
    }

    return null;
  }
}
