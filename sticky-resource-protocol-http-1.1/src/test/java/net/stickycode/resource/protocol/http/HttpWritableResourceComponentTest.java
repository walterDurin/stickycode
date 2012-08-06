package net.stickycode.resource.protocol.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.Uncontrolled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.codec.StringResourceCodec;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.net.httpserver.HttpServer;

@RunWith(MockwireRunner.class)
public class HttpWritableResourceComponentTest {

  @Controlled
  ResourceLocation location;

  @UnderTest
  HttpResourceProtocol protocol;

  @Uncontrolled
  HttpClientComponent component;

  @Test
  public void writable()
      throws IOException, InterruptedException {
    HttpServer server = HttpServerFactory.create("http://localhost:9878/test", new DefaultResourceConfig() {

      @Override
      public Set<Class<?>> getRootResourceClasses() {
        return Collections.<Class<?>> singleton(WritableTestResource.class);
      }
    });
    server.start();

    try {
      // File http = File.createTempFile("sampleStream", ".txt");
      when(location.getPath()).thenReturn("localhost:9878/test/resource/10");
      ResourceOutput output = protocol.createOutput(location);
      String message = "written to resource location";
      output.store(message, null, new StringResourceCodec());
      output.close();

      ResourceInput in = protocol.createInput(location);
      String value = in.load(CoercionTargets.find(String.class), new StringResourceCodec(), Charset.forName("UTF-8"));
      assertThat(value).isEqualTo(message);
      in.close();
    }
    finally {
      server.stop(5);
    }
  }
}
