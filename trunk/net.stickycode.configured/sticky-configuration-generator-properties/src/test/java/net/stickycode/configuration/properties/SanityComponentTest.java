package net.stickycode.configuration.properties;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment("net.stickycode.metadata")
@MockwireConfigured({"propertiesConfigurationGenerator.directory=${java.io.tmpdir}"})
public class SanityComponentTest {

  @Inject
  ConfigurationSystem system;
  
  @Inject
  PropertiesConfigurationGenerator generator;
  
  @Test
  public void check() {
    system.configure();
  }
}
