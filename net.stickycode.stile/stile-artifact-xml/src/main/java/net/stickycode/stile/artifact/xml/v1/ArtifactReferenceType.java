package net.stickycode.stile.artifact.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArtifactReferenceType")
public class ArtifactReferenceType {

  @XmlAttribute
  protected String id;

  @XmlAttribute
  protected String version;

  public String getId() {
    return id;
  }

  void setId(String value) {
    this.id = value;
  }

  public String getVersion() {
    return version;
  }

  void setVersion(String value) {
    this.version = value;
  }

}
