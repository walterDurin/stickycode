package net.stickycode.resource.protocol;

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
public class FileResourceProtocolTest {

  @Mock
  ResourceLocation location;

  FileResourceProtocol protocol = new FileResourceProtocol();

  @Test
  public void matching() {
    assertThat(protocol.canResolve("file")).isTrue();
    assertThat(protocol.canResolve("http")).isFalse();
    assertThat(protocol.canResolve("folder")).isFalse();
  }

  @Test(expected = ResourceNotFoundException.class)
  public void notExists() {
    when(location.getPath()).thenReturn("src/test/resources/nonexistant");
    protocol.createConnection(location).getInputStream();
  }

  @Test
  public void readable() throws IOException {
    when(location.getPath()).thenReturn("src/test/resources/sampleStream.txt");
    InputStream in = protocol.createConnection(location).getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    assertThat(reader.readLine()).isEqualTo("sample text in a file");
    in.close();
  }

  @Test
  public void writable() throws IOException {
    File file = File.createTempFile("sampleStream", ".txt");
    when(location.getPath()).thenReturn(file.getAbsolutePath());
    OutputStream out = protocol.createConnection(location).getOutputStream();
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    String message = "written to resource location " + file.getAbsolutePath();
    writer.write(message);
    writer.close();

    InputStream in = protocol.createConnection(location).getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    assertThat(reader.readLine()).isEqualTo(message);
    in.close();
  }

  @Test(expected = ResourcePathNotFoundForWriteException.class)
  public void notWritable() throws IOException {
    when(location.getPath()).thenReturn("/some/random/path/file.txt");
    OutputStream out = protocol.createConnection(location).getOutputStream();
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    String message = "should never go anywhere";
    writer.write(message);
    writer.close();
  }
}
