package net.stickycode.stile.version.component;

public enum VersionDefinition {

  FCS,
  RC,
  BETA,
  ALPHA;

  static VersionDefinition fromCode(String code) {
    return valueOf(code.toUpperCase());
  }

}
