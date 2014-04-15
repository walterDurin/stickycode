package net.stickycode.rs.coercion.jersey;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response.Status.Family;

import net.stickycode.stereotype.failure.FailureClassification;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class JerseyClientProxy
    implements InvocationHandler {

  private Logger log;

  private Client client;

  private String baseUrl;

  public JerseyClientProxy(Class<?> type, String baseUrl) {
    ClientConfig configuration = new DefaultClientConfig();
    configuration.getClasses().add(JacksonJsonProvider.class);
    this.client = Client.create(configuration);
    this.client.setConnectTimeout(1000);
    this.client.setReadTimeout(10000);
    this.client.addFilter(new LoggingFilter());
    this.baseUrl = baseUrl;
    this.log = LoggerFactory.getLogger(type);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {

    RequestMetadata request = new RequestMetadata(method, args);

    WebResource resource = client
        .resource(baseUrl)
        .path(request.getOperationPath());

    log.info("url {}", resource.getURI());

    Builder target = resource
        .accept(request.getIngoingMediaType())
        .type(request.getOutgoingMediaType());

    ClientResponse response = call(target, request);

    if (Family.SUCCESSFUL.equals(response.getClientResponseStatus().getFamily()))
      if (void.class.isAssignableFrom(method.getReturnType()))
        return null;
      else
        return response.getEntity(method.getReturnType());

    FailureClassification classification = new FailureClassificationMapper().resolveClassification(response.getStatus());
    // XXX need to validate the X-Failure-Code header
    throw new FailureMetadata(method.getExceptionTypes()).resolve(classification);
  }

  private ClientResponse call(Builder target, RequestMetadata request) {
    if (request.hasPayload())
      return target.method(request.getMethod(), ClientResponse.class, request.getPayload());

    return target.method(request.getMethod(), ClientResponse.class);
  }

}
