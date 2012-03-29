package net.stickycode.rant.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.stickycode.stile.version.VersionRange;

public class VersionRangeXmlJavaTypeAdapter
    extends XmlAdapter<String, VersionRange> {

  @Override
  public VersionRange unmarshal(String v) throws Exception {
    return null;
  }

  @Override
  public String marshal(VersionRange v) throws Exception {
    return null;
  }

}
