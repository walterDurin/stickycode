package net.stickycode.resource.protocol.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.Uncontrolled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;
import net.stickycode.resource.ResourceResolutionFailedException;
import net.stickycode.resource.codec.StringResourceCodec;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
public class HttpResourceProtocolTest {

  @Controlled
  ResourceLocation location;

  @UnderTest
  HttpResourceProtocol protocol;

  @Uncontrolled
  HttpClientComponent component;

  @Test
  public void matching() {
    assertThat(protocol.canResolve("http")).isTrue();
  }

  @Test
  public void notMatching() {
    assertThat(protocol.canResolve("file")).isFalse();
    assertThat(protocol.canResolve("blah")).isFalse();
    assertThat(protocol.canResolve("folder")).isFalse();
    assertThat(protocol.canResolve("classpath")).isFalse();
  }

  @Test(expected = ResourceResolutionFailedException.class)
  public void notExists() {
    when(location.getPath()).thenReturn("xyu.sdfa.localhost");
    protocol.createInput(location).load(null, null, Charset.forName("UTF-8"));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void notFound() throws IOException {
    when(location.getPath()).thenReturn("localhost/not/here.html");
    protocol.createInput(location).load(null, null, Charset.forName("UTF-8"));
  }

  @Test
  public void readable() throws IOException {
    when(location.getPath()).thenReturn("www.redengine.co.nz/index.html");
    ResourceInput in = protocol.createInput(location);
    String value = in.load(null, new StringResourceCodec(), Charset.forName("UTF-8"));
    assertThat(value).contains("DOCTYPE");
    in.close();
  }

  @Test(expected = ResourcePathNotFoundForWriteException.class)
  public void notWritable() throws IOException {
    when(location.getPath()).thenReturn("nosuchdomain.nothere/random/path/http.txt");
    ResourceOutput out = protocol.createOutput(location);
    out.store("should never go anywhere", null, new StringResourceCodec());
  }
}
