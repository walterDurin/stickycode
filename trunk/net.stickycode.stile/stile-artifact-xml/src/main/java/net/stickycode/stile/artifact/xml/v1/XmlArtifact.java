package net.stickycode.stile.artifact.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "artifact")
@XmlType(name = "ArtifactType", propOrder = {
    "group",
    "id",
    "version",
    "packaging",
    "project",
    "dependencies"
})
public class XmlArtifact {

  @XmlElement
  private String group;

  @XmlElement
  private String id;

  @XmlElement
  private String version;

  @XmlElement
  private ArtifactReferenceType packaging;

  @XmlElement
  private ArtifactReferenceType project;

  @XmlElement
  private DependenciesType dependencies;

  public String getId() {
    return id;
  }

  public String getGroup() {
    return group;
  }

  public String getVersion() {
    return version;
  }

  public ArtifactReferenceType getPackaging() {
    return packaging;
  }

  public ArtifactReferenceType getProject() {
    return project;
  }

  public DependenciesType getDependencies() {
    return dependencies;
  }

  void setVersion(String version) {
    this.version = version;
  }

  void setPackaging(ArtifactReferenceType packaging) {
    this.packaging = packaging;
  }

  void setProject(ArtifactReferenceType project) {
    this.project = project;
  }

  void setDependencies(DependenciesType dependencies) {
    this.dependencies = dependencies;
  }

  void setId(String id) {
    this.id = id;
  }

  void setGroup(String group) {
    this.group = group;
  }
}
