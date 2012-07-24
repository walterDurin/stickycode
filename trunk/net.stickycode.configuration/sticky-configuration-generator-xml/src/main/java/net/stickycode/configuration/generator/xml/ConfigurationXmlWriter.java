package net.stickycode.configuration.generator.xml;

import java.io.StringWriter;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.stickycode.configured.ConfigurationListener;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.stereotype.StickyPlugin;

@StickyPlugin
public class ConfigurationXmlWriter
    implements ConfigurationListener {

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
