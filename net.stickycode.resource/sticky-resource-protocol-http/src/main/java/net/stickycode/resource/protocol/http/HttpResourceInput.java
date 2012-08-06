package net.stickycode.resource.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceResolutionFailedException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpResourceInput
    implements ResourceInput {

  private HttpClient client;

  private ResourceLocation resourceLocation;

  public HttpResourceInput(HttpClient client, ResourceLocation resourceLocation) {
    this.client = client;
    this.resourceLocation = resourceLocation;
  }

  @Override
  public void close()
      throws IOException {
  }

  private InputStream getContent(HttpResponse execute) {
    try {
      return execute.getEntity().getContent();
    }
    catch (IllegalStateException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpResponse executeGet(ResourceLocation resourceLocation) {
    HttpGet request = new HttpGet("http://" + resourceLocation.getPath());
    try {
      HttpResponse execute = client.execute(request);
      StatusLine statusLine = execute.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      switch (statusCode) {
      case 200:
        return execute;
      case 404:
        throw new ResourceNotFoundException(statusLine.getReasonPhrase());
      default:
        throw new RuntimeException(statusLine.toString());
      }
    }
    catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    }
    catch (UnknownHostException e) {
      throw new ResourceResolutionFailedException(e, resourceLocation);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T load(CoercionTarget resourceTarget, ResourceCodec<T> codec, Charset characterSet) {
    HttpResponse execute = executeGet(resourceLocation);
    InputStream input = getContent(execute);
    try {
      return codec.load(resourceTarget, input, characterSet);
    }
    finally {
      try {
        input.close();
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
