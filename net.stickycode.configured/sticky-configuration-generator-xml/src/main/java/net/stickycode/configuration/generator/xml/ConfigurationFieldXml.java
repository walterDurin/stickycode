package net.stickycode.configuration.generator.xml;

import javax.xml.bind.annotation.XmlAttribute;

import net.stickycode.configured.ConfigurationAttribute;

class ConfigurationFieldXml {

  private ConfigurationAttribute attribute;

  public ConfigurationFieldXml withAttribute(ConfigurationAttribute a) {
    this.attribute = a;
    return this;
  }
  
  @XmlAttribute
  public String getType() {
    return attribute.getType().getName();
  }
  
  @XmlAttribute
  public String getName() {
    return attribute.getName();
  }
  
  @XmlAttribute
  public String getValue() {
    return attribute/* .getValue() */ .toString();
  }
  
  @XmlAttribute
  public String getDefaultValue() {
    return attribute.getDefaultValue().toString();
  }

}