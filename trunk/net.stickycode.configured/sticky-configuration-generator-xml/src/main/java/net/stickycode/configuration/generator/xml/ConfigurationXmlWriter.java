package net.stickycode.configuration.generator.xml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationAttribute;
import net.stickycode.configured.ConfigurationListener;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.stereotype.StickyPlugin;

@StickyPlugin
public class ConfigurationXmlWriter
    implements ConfigurationListener {
  
  static class ConfigurationFieldXml {

    private ConfigurationAttribute attribute;

    public ConfigurationFieldXml withAttribute(ConfigurationAttribute a) {
      this.attribute = a;
      return this;
    }
    
    public String getName() {
      return attribute.getName();
    }

  }

  static class ConfigurationBeanXml {

    private Configuration configuration;
    private List<ConfigurationFieldXml> fields;
    
    public ConfigurationBeanXml withConfiguation(Configuration c) {
      this.configuration = c;
      List<ConfigurationFieldXml> f = new ArrayList<ConfigurationXmlWriter.ConfigurationFieldXml>();
      for (ConfigurationAttribute a : c) {
        f.add(new ConfigurationFieldXml().withAttribute(a));
      }
      fields = f;
      return this;
    }

    @XmlElement
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

  @XmlRootElement(name = "sticky")
  static class ConfigurationXml {

    @XmlAttribute
    private Date generatedAt = new Date();
    
    private ConfigurationRepository repository;

    public ConfigurationXml withConfiguration(ConfigurationRepository repository) {
      this.repository = repository;
      return this;
    }

    @XmlElement(name = "bean")
    public List<ConfigurationBeanXml> getBeans() {
      List<ConfigurationBeanXml> beans = new LinkedList<ConfigurationXmlWriter.ConfigurationBeanXml>();
      for (Configuration c : repository) {
        beans.add(new ConfigurationBeanXml().withConfiguation(c));
      }
      return beans;
    }

  }

  @Inject
  private ConfigurationRepository repository;

  public String toXml() {
    Marshaller marshaller = createContext();
    StringWriter writer = new StringWriter();
    try {
      marshaller.marshal(new ConfigurationXml().withConfiguration(repository), writer);
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    return writer.toString();
  }

  private Marshaller createContext() {
    try {
      JAXBContext context = JAXBContext.newInstance(ConfigurationXml.class);
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.formatted.output", true);
      return marshaller;
    }
    catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void resolve() {
  }

  @Override
  public void preConfigure() {
  }

  @Override
  public void configure() {
  }

  @Override
  public void postConfigure() {
    System.out.println(toXml());
  }

}
