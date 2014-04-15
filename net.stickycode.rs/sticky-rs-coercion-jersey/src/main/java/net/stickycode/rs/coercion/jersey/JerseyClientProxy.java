package net.stickycode.rs.coercion.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

public class JerseyClientProxy
    implements InvocationHandler {

  private Logger log;

  private Client client;

  private String baseUrl;

  public JerseyClientProxy(Class<?> type, String baseUrl) {
    ClientConfig configuration = new DefaultClientConfig();
    configuration.getClasses().add(JacksonJsonProvider.class);
    this.client = Client.create(configuration);
    this.baseUrl = baseUrl;
    this.log = LoggerFactory.getLogger(type);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
    String outgoing = deriveOutgoingMediaType(method.getAnnotation(Consumes.class));
    String incoming = deriveIncomingMediaType(method.getAnnotation(Produces.class));

    WebResource path = client.resource(baseUrl);

    path = resolveTemplate(path, method.getDeclaringClass(), method, args);
    path = resolveTemplate(path, method, method, args);

    Builder target = path.accept(incoming).type(outgoing);

    log.info("url {}", path.getURI());

    if (method.isAnnotationPresent(GET.class)) {
      ClientResponse response = target.get(ClientResponse.class);
      if (Family.SUCCESSFUL.equals(response.getClientResponseStatus().getFamily()))
        return response.getEntity(method.getReturnType());

      FailureClassification classification = new FailureClassificationMapper().resolveClassification(response.getStatus());
      throw new FailureMetadata(method.getExceptionTypes()).resolve(classification);
    }

    if (method.isAnnotationPresent(POST.class)) {
      ClientResponse response = target.post(ClientResponse.class, args[args.length - 1]);
      if (Family.SUCCESSFUL.equals(response.getClientResponseStatus().getFamily()))
        return response.getEntity(method.getReturnType());

      FailureClassification classification = new FailureClassificationMapper().resolveClassification(response.getStatus());
      throw new FailureMetadata(method.getExceptionTypes()).resolve(classification);
    }
    
    if (method.isAnnotationPresent(DELETE.class)) {
      ClientResponse response = target.delete(ClientResponse.class);
      if (Family.SUCCESSFUL.equals(response.getClientResponseStatus().getFamily()))
        if (void.class.isAssignableFrom(method.getReturnType()))
          return null;
        else
          return response.getEntity(method.getReturnType());
      
      FailureClassification classification = new FailureClassificationMapper().resolveClassification(response.getStatus());
      throw new FailureMetadata(method.getExceptionTypes()).resolve(classification);
    }

    throw new UnsupportedOperationException("Operation " + method.getName() + " not supported");
  }

  private String deriveIncomingMediaType(Produces annotation) {
    if (annotation == null)
      return MediaType.APPLICATION_JSON;

    return annotation.value()[0];
  }

  private String deriveOutgoingMediaType(Consumes annotation) {
    if (annotation == null)
      return MediaType.APPLICATION_JSON;

    return annotation.value()[0];
  }

  private WebResource resolveTemplate(WebResource url, AnnotatedElement element, Method method, Object[] args) {
    Path annotation = element.getAnnotation(Path.class);
    if (annotation == null)
      return url;

    String path = annotation.value();
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    for (int i = 0; i < parameterAnnotations.length; i++) {
      for (Annotation a : parameterAnnotations[i]) {
        if (a instanceof PathParam) {
          String propertyName = ((PathParam) a).value();
          path = resolveValue(path, propertyName, args[i]);
        }
      }
    }

    return url.path(path);
  }

  private String resolveValue(String path, String propertyName, Object object) {
    return path.replaceAll("\\{" + propertyName + "\\}", object.toString());
  }

}
