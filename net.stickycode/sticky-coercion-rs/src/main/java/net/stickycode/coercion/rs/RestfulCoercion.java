package net.stickycode.coercion.rs;

import java.util.Collections;

import javax.ws.rs.Path;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.plugin.StickyExtension;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

@StickyExtension
public class RestfulCoercion
    extends AbstractNoDefaultCoercion<Object> {
  
  @Override
  public Object coerce(CoercionTarget target, String value) {

    Object client = JAXRSClientFactory.create(value,
        target.getType(),
        Collections.singletonList(new StatusToFailureResolver()));

    WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor());
    WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor());

    return client;
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!target.getType().isInterface())
      return false;

    return target.getAnnotatedElement().isAnnotationPresent(Path.class);
  }

}
