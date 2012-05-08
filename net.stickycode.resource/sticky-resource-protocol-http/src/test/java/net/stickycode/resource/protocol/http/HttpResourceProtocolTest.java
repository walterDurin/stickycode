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

import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HttpResourceProtocolTest {

  @Mock
  ResourceLocation location;

  HttpResourceProtocol protocol = new HttpResourceProtocol();

  @Test
  public void matching() {
    assertThat(protocol.canResolve("http://")).isTrue();
    assertThat(protocol.canResolve("http:///")).isTrue();
    assertThat(protocol.canResolve("http://to/some/path")).isTrue();
    assertThat(protocol.canResolve("http:to/some/path")).isFalse();
  }
  
  @Test
  public void notMatching() {
    assertThat(protocol.canResolve("folder:to/some/path")).isFalse();
    assertThat(protocol.canResolve("file:to/some/path")).isFalse();
    assertThat(protocol.canResolve("classpath:to/some/path")).isFalse();
    assertThat(protocol.canResolve(":to/some/path")).isFalse();
    assertThat(protocol.canResolve("://to/some/path")).isFalse();
    assertThat(protocol.canResolve("ftp://to/some/path")).isFalse();
  }

  @Test(expected = ResourceNotFoundException.class)
  public void notExists() {
    when(location.getPath()).thenReturn("http://src/test/resources/nonexistant");
    protocol.getInputStream(location);
  }

  @Test
  public void readable() throws IOException {
    when(location.getPath()).thenReturn("http://src/test/resources/sampleStream.txt");
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
