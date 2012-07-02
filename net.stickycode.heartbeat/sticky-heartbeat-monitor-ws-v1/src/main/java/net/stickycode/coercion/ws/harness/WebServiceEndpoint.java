package net.stickycode.coercion.ws.harness;

import javax.xml.ws.Endpoint;

public class WebServiceEndpoint<C> {

  private final Endpoint endpoint;

  private final C client;


  public WebServiceEndpoint(Endpoint endpoint, C client) {
    super();
    this.endpoint = endpoint;
    this.client = client;
  }

  // @Unconfigure
  public void stop() {
    endpoint.stop();
  }

  public Endpoint getEndpoint() {
    return endpoint;
  }

  public C getClient() {
    return client;
  }
}
