package net.stickycode.configuration.generator.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationAttribute;

class ConfigurationBeanXml {

  private Configuration configuration;
  private List<ConfigurationFieldXml> fields;
  
  public ConfigurationBeanXml withConfiguation(Configuration c) {
    this.configuration = c;
    List<ConfigurationFieldXml> f = new ArrayList<ConfigurationFieldXml>();
    for (ConfigurationAttribute a : c) {
      f.add(new ConfigurationFieldXml().withAttribute(a));
    }
    fields = f;
    return this;
  }

  @XmlElements(@XmlElement(name="field"))
  public List<ConfigurationFieldXml> getFields() {
    return fields;
  }

  @XmlAttribute
  public String getType() {
    return configuration.getType().getName();
  }

  @XmlAttribute
  public String getName() {
    return configuration.getName();
  }

}