package net.stickycode.resource.protocol;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;
import net.stickycode.resource.codec.StringResourceCodec;

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
    protocol.createInput(location);
  }

  @Test
  public void readable() throws IOException {
    when(location.getPath()).thenReturn("src/test/resources/sampleStream.txt");
    ResourceInput in = protocol.createInput(location);
    String value = in.load(null, new StringResourceCodec(), Charset.forName("UTF-8"));
    assertThat(value).isEqualTo("sample text in a file");
    in.close();
  }

  @Test
  public void writable() throws IOException {
    File file = File.createTempFile("sampleStream", ".txt");
    when(location.getPath()).thenReturn(file.getAbsolutePath());
    ResourceOutput out = protocol.createOutput(location);
    String message = "written to resource location " + file.getAbsolutePath();
    out.store(message, null, new StringResourceCodec());
    out.close();
    
    ResourceInput in = protocol.createInput(location);
    String value = in.load(null, new StringResourceCodec(), Charset.forName("UTF-8"));
    assertThat(value).isEqualTo(message);
    in.close();
  }

  @Test(expected = ResourcePathNotFoundForWriteException.class)
  public void notWritable() throws IOException {
    when(location.getPath()).thenReturn("/some/random/path/file.txt");
    ResourceOutput out = protocol.createOutput(location);
    String message = "what path?";
    out.store(message, null, new StringResourceCodec());
  }
}
