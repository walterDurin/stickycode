package net.stickycode.configuration.generator.xml;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationRepository;

@XmlRootElement(name = "sticky") 
class ConfigurationXml {

  @XmlAttribute
  private Date generatedAt = new Date();
  
  private ConfigurationRepository repository;

  public ConfigurationXml withConfiguration(ConfigurationRepository repository) {
    this.repository = repository;
    return this;
  }

  @XmlElement(name = "bean")
  public List<ConfigurationBeanXml> getBeans() {
    List<ConfigurationBeanXml> beans = new LinkedList<ConfigurationBeanXml>();
    for (Configuration c : repository) {
      beans.add(new ConfigurationBeanXml().withConfiguation(c));
    }
    return beans;
  }

}