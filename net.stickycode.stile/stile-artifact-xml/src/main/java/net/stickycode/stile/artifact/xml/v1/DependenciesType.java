package net.stickycode.stile.artifact.xml.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DependenciesType", propOrder = {
    "unittest",
    "componenttest",
    "integrationtest",
    "main"
})
public class DependenciesType {

  @XmlElement
  private DependencyScopeType unittest;

  @XmlElement
  private DependencyScopeType componenttest;

  @XmlElement
  private DependencyScopeType integrationtest;

  @XmlElement
  private DependencyScopeType main;

  public DependencyScopeType getUnittest() {
    return unittest;
  }

  void setUnittest(DependencyScopeType value) {
    this.unittest = value;
  }

  public DependencyScopeType getComponenttest() {
    return componenttest;
  }

  void setComponenttest(DependencyScopeType value) {
    this.componenttest = value;
  }

  public DependencyScopeType getIntegrationtest() {
    return integrationtest;
  }

  void setIntegrationtest(DependencyScopeType value) {
    this.integrationtest = value;
  }

  public DependencyScopeType getMain() {
    return main;
  }

  void setMain(DependencyScopeType value) {
    this.main = value;
  }

}
