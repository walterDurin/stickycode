package net.stickycode.resource.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourcePathNotFoundForWriteException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.util.EntityUtils;

public class HttpResourceOutput
    implements ResourceOutput {

  private HttpClient client;

  private ResourceLocation resourceLocation;
  
  private ResourceEntity entity;

  public HttpResourceOutput(HttpClient client, ResourceLocation resourceLocation) {
    this.client = client;
    this.resourceLocation = resourceLocation;
  }

  @Override
  public void close()
      throws IOException {
  }
  
  @Override
  public <T> void store(T value, CoercionTarget type, ResourceCodec<T> codec) {
    HttpPut request = new HttpPut("http://" + resourceLocation.getPath());

    try {
//      request.expectContinue(); XXX check if this is a good idea
      request.setEntity(new ResourceEntity(value, type, codec, Charset.forName("UTF-8")));
      HttpResponse execute = client.execute(request);
      EntityUtils.consume(execute.getEntity());
      StatusLine statusLine = execute.getStatusLine();
      if (statusLine.getStatusCode() != 201) {
        throw new ResourceWriteFailedException(statusLine);
      }
    }
    catch (UnknownHostException e) {
      throw new ResourcePathNotFoundForWriteException(e, resourceLocation);
    }
    catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
