package net.stickycode.resource.protocol.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import net.stickycode.mockwire.Controlled;
import net.stickycode.mockwire.Uncontrolled;
import net.stickycode.mockwire.UnderTest;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;
import net.stickycode.resource.ResourceResolutionFailedException;

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
    when(location.getPath()).thenReturn("xyu.sdfa.doesnotexistoiasdpf");
    protocol.getInputStream(location);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void notFound() throws IOException {
    when(location.getPath()).thenReturn("localhost/not/here.html");
    InputStream in = protocol.getInputStream(location);
  }
  
  @Test
  public void readable() throws IOException {
    when(location.getPath()).thenReturn("www.redengine.co.nz/index.html");
    InputStream in = protocol.getInputStream(location);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    assertThat(reader.readLine()).isEqualTo("sample text in a http");
    in.close();
  }

  @Test
  public void writable() throws IOException {
    File http = File.createTempFile("sampleStream", ".txt");
    when(location.getPath()).thenReturn("http://" + http.getAbsolutePath());
    OutputStream out = protocol.getOutputStream(location);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    String message = "written to resource location " + http.getAbsolutePath();
    writer.write(message);
    writer.close();

    InputStream in = protocol.getInputStream(location);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    assertThat(reader.readLine()).isEqualTo(message);
    in.close();
  }

  @Test(expected = ResourcePathNotFoundForWriteException.class)
  public void notWritable() throws IOException {
    when(location.getPath()).thenReturn("http:///some/random/path/http.txt");
    OutputStream out = protocol.getOutputStream(location);
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    String message = "should never go anywhere";
    writer.write(message);
    writer.close();
  }
}
