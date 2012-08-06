package net.stickycode.resource.protocol.http;

import javax.inject.Inject;

import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.apache.http.client.HttpClient;

@StickyExtension
public class HttpResourceProtocol
    implements ResourceProtocol {

  @Inject
  private HttpClient client;

  @Override
  public boolean canResolve(String protocol) {
    return protocol.startsWith("http");
  }

//  static class HttpResourceConnection<T>
//      implements ResourceConnection<T>, HttpEntity {
//
//    private HttpClient client;
//
//    private ResourceLocation resourceLocation;
//
//    private ResourceCodec<T> codec;
//
//    public HttpResourceConnection(HttpClient client, ResourceLocation location, ResourceCodec<T> codec) {
//      super();
//      this.client = client;
//      this.resourceLocation = location;
//      this.codec = codec;
//    }
//
//    @Override
//    public InputStream getInputStream() {
//
//      HttpResponse execute = executeGet(resourceLocation);
//      return getContent(execute);
//    }
//
//    private InputStream getContent(HttpResponse execute) {
//      try {
//        return execute.getEntity().getContent();
//      }
//      catch (IllegalStateException e) {
//        throw new RuntimeException(e);
//      }
//      catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//    private HttpResponse executeGet(ResourceLocation resourceLocation) {
//      HttpGet request = new HttpGet("http://" + resourceLocation.getPath());
//      try {
//        HttpResponse execute = client.execute(request);
//        StatusLine statusLine = execute.getStatusLine();
//        int statusCode = statusLine.getStatusCode();
//        switch (statusCode) {
//        case 200:
//          return execute;
//        case 404:
//          throw new ResourceNotFoundException(statusLine.getReasonPhrase());
//        default:
//          throw new RuntimeException(statusLine.toString());
//        }
//      }
//      catch (ClientProtocolException e) {
//        throw new RuntimeException(e);
//      }
//      catch (UnknownHostException e) {
//        throw new ResourceResolutionFailedException(e, resourceLocation);
//      }
//      catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//    @Override
//    public Charset getCharacterSet() {
//      return Charset.forName("UTF-8");
//    }
//
//    @Override
//    public OutputStream getOutputStream() {
//      HttpPut request = new HttpPut("http://" + resourceLocation.getPath());
//
//      try {
//        request.setEntity(this);
//        HttpResponse execute = client.execute(request);
//        StatusLine statusLine = execute.getStatusLine();
//        throw new RuntimeException();
//      }
//      catch (UnknownHostException e) {
//        throw new ResourcePathNotFoundForWriteException(e, resourceLocation);
//      }
//      catch (ClientProtocolException e) {
//        throw new RuntimeException(e);
//      }
//      catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//    @Override
//    public ResourceLocation getLocation() {
//      return resourceLocation;
//    }
//
//    @Override
//    public void consumeContent() throws IOException {
//    }
//
//    @Override
//    public InputStream getContent() throws IOException, IllegalStateException {
//      return getInputStream();
//    }
//
//    @Override
//    public Header getContentEncoding() {
//      return null;
//    }
//
//    @Override
//    public long getContentLength() {
//      return 0;
//    }
//
//    @Override
//    public Header getContentType() {
//      return null;
//    }
//
//    @Override
//    public boolean isChunked() {
//      return false;
//    }
//
//    @Override
//    public boolean isRepeatable() {
//      return false;
//    }
//
//    @Override
//    public boolean isStreaming() {
//      return false;
//    }
//
//    @Override
//    public void writeTo(OutputStream arg0) throws IOException {
//      codec.store(sourceType, resource, target);
//    }
//
//    @Override
//    public void store(T content) {
//    }
//  }

  @Override
  public ResourceInput createInput(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    return new HttpResourceInput(client, resourceLocation);
  }

  @Override
  public ResourceOutput createOutput(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    return new HttpResourceOutput(client, resourceLocation);
  }

}
