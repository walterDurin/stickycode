package net.stickycode.rant.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.stickycode.stile.version.VersionRange;


public class Defect
    extends Rant {

  @XmlElement
  @XmlJavaTypeAdapter(value=VersionRangeXmlJavaTypeAdapter.class)
  VersionRange affects;
  
  @XmlAttribute
  String reference;
  
  @XmlAttribute
  String summary;
  
  @XmlElement
  String description;
  
  @XmlElement
  String rootCause;
  
}
