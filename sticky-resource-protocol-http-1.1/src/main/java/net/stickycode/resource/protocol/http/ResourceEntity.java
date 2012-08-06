package net.stickycode.resource.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;

public class ResourceEntity<T>
    implements HttpEntity {

  private ResourceCodec<T> codec;

  private T value;

  private CoercionTarget target;

  private Charset characterSet;

  public ResourceEntity(T value, CoercionTarget type, ResourceCodec<T> codec, Charset characterSet) {
    this.value = value;
    this.codec = codec;
    this.target = type;
    this.characterSet = characterSet;
  }

  @Override
  public void consumeContent()
      throws IOException {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public InputStream getContent()
      throws IOException, IllegalStateException {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Header getContentEncoding() {
    return null;
  }

  @Override
  public long getContentLength() {
    return -1;
  }

  @Override
  public Header getContentType() {
   return null;
  }

  @Override
  public boolean isChunked() {
    return false;
  }

  @Override
  public boolean isRepeatable() {
    return false;
  }

  @Override
  public boolean isStreaming() {
    return false;
  }

  @Override
  public void writeTo(OutputStream output)
      throws IOException {
    codec.store(target, value, output, characterSet);
    output.close();
  }
}
