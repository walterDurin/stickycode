package net.stickycode.resource.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.inject.Inject;

import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceResolutionFailedException;
import net.stickycode.stereotype.component.StickyExtension;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

@StickyExtension
public class HttpResourceProtocol
    implements ResourceProtocol {

  @Inject
  private HttpClient client;

  @Override
  public InputStream getInputStream(ResourceLocation resourceLocation) throws ResourceNotFoundException {

    HttpResponse execute = execute(resourceLocation);
    return getContent(execute);
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

  private HttpResponse execute(ResourceLocation resourceLocation) {
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
  public boolean canResolve(String protocol) {
    return protocol.startsWith("http");
  }

  @Override
  public OutputStream getOutputStream(ResourceLocation uriResourceLocation) {
    return null;
  }

}
