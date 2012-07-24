package net.stickycode.configuration.properties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.inject.Inject;

import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationAttribute;
import net.stickycode.configured.ConfigurationKeyBuilder;
import net.stickycode.configured.ConfigurationListener;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.StickyComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyComponent
public class PropertiesConfigurationGenerator
    implements ConfigurationListener {

  private Logger log = LoggerFactory.getLogger(getClass());
  
  @Configured
  private File directory;

  @Configured
  private Charset characterSet = Charset.forName("UTF-8");

  @Inject
  private ConfigurationRepository repository;

  @Inject
  private ConfigurationKeyBuilder keyBuilder;
  
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
    log.info("{}", directory);
    try {
      BufferedWriter writer = createWriter();
      write(writer);
      writer.close();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void write(BufferedWriter writer) throws IOException {
    for (Configuration bean : repository) {
      appendBeanName(writer, bean);
      for (ConfigurationAttribute attribute : bean) {
        writeAttribute(writer, bean, attribute);
      }
      writer.newLine();
    }
  }

  private void writeAttribute(BufferedWriter writer, Configuration bean, ConfigurationAttribute attribute) throws IOException {
    String key = keyBuilder.build(bean, attribute);
    if (attribute.hasDefaultValue())
      if (!attribute.getValue().equals(attribute.getDefaultValue()))
        writeDefaultValue(writer, key, attribute.getDefaultValue().toString());

    writeAttribute(writer, key, attribute.getValue().toString());
  }

  private void writeDefaultValue(BufferedWriter writer, String key, String value) throws IOException {
    writer.append("# ");
    writeAttribute(writer, key, value);
  }

  private void writeAttribute(BufferedWriter writer, String key, String value) throws IOException {
    writer.append(key).append("=").append(value);
    writer.newLine();
  }

  private void appendBeanName(BufferedWriter writer, Configuration bean) throws IOException {
    writer.append("# Bean ").append(bean.getName()).append(" (").append(bean.getType().getName()).append(")");
    writer.newLine();
  }

  private BufferedWriter createWriter() throws IOException {
    File file = new File(directory, "configured-" + System.currentTimeMillis() + ".properties");
    log.info("writing configuration to {}", file);
    return new BufferedWriter(
        new OutputStreamWriter(
            new FileOutputStream(
                file
            ), characterSet));
  }

}
