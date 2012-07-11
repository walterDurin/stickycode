package co.nfigured.example.resource;

import org.junit.runner.RunWith;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.Configured;

@RunWith(MockwireRunner.class)
@MockwireConfigured("verbose=http://localhost:8999/rs/someResources")
public class SomeResourcesIntegrationTest {
  
  @Configured
  SomeResource verbose;
}
