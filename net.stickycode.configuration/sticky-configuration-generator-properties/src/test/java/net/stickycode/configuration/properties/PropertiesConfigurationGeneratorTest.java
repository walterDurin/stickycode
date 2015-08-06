package net.stickycode.configuration.properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.beans.Introspector;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationAttribute;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfiguredBeanProcessor;
import net.stickycode.configured.ConfiguredConfiguration;
import net.stickycode.configured.ConfiguredField;
import net.stickycode.configured.SimpleNameDotFieldConfigurationKeyBuilder;
import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.Uncontrolled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.reflector.Fields;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
public class PropertiesConfigurationGeneratorTest {

  String value = "default";

  String value2 = "other";

  @Controlled
  private ConfigurationRepository configuration;

  @UnderTest
  private PropertiesConfigurationGenerator generator;

  @Uncontrolled
  private SimpleNameDotFieldConfigurationKeyBuilder keyBuilder;

  @Test
  public void empty() throws IOException {
    when(configuration.iterator()).thenReturn(Collections.<Configuration> emptyList().iterator());
    assertThat(xml()).isEmpty();
  }

  @Test
  public void justBean() throws IOException {
    when(configuration.iterator()).thenReturn(iterator());
    assertThat(xml())
        .contains("# Bean propertiesConfigurationGeneratorTest");
    ;
  }

  @Test
  public void beanWithOneField() throws IOException {
    when(configuration.iterator()).thenReturn(iterator(field("value")));
    assertThat(xml())
        .contains("# Bean propertiesConfigurationGeneratorTest")
        .contains("propertiesConfigurationGeneratorTest.value=default");
  }

  @Test
  public void beanWithTwoField() throws IOException {
    when(configuration.iterator()).thenReturn(iterator(field("value"), field("value2")));
    assertThat(xml())
        .contains("# Bean propertiesConfigurationGeneratorTest")
        .contains("propertiesConfigurationGeneratorTest.value=default")
        .contains("propertiesConfigurationGeneratorTest.value2=other");
  }

  @Test
  public void beansWithTwoField() throws IOException {
    when(configuration.iterator()).thenReturn(iterator(field("value"), field("value2")));
    assertThat(xml())
        .contains("# Bean propertiesConfigurationGeneratorTest")
        .contains("propertiesConfigurationGeneratorTest.value=default")
        .contains("propertiesConfigurationGeneratorTest.value2=other");
  }

  private String xml() throws IOException {
    StringWriter out = new StringWriter();
    BufferedWriter writer = new BufferedWriter(new PrintWriter(out));
    generator.write(writer);
    writer.flush();
    return out.toString();
  }

  private ConfigurationAttribute field(String fieldName) {
    Field find = Fields.find(getClass(), fieldName);
    return new ConfiguredField(this, find, CoercionTargets.find(find));
  }

  private Iterator<Configuration> iterator(ConfigurationAttribute... attributes) {
    List<Configuration> list = new ArrayList<Configuration>();
    ConfiguredConfiguration configuration = configuration(attributes);
    list.add(configuration);
    return list.iterator();
  }

  private ConfiguredConfiguration configuration(ConfigurationAttribute... attributes) {
    ConfiguredConfiguration configuration = new ConfiguredConfiguration(this, Introspector.decapitalize(getClass().getSimpleName()));
    for (ConfigurationAttribute a : attributes) {
      configuration.addAttribute(a);
    }
    return configuration;
  }

}
