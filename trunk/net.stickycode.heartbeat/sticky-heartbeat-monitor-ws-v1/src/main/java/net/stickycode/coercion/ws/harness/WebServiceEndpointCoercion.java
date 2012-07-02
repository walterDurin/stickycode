package net.stickycode.coercion.ws.harness;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.ws.WebServiceCoercion;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class WebServiceEndpointCoercion
    extends AbstractNoDefaultCoercion<WebServiceEndpoint<?>> {

  @Inject
  private BeanFinder finder;

  @Inject
  private WebServiceCoercion webServiceCoercion;

  @Override
  public WebServiceEndpoint<?> coerce(CoercionTarget type, String value) {
    Object implementor = finder.find(type.getComponentCoercionTypes()[0].getType());
    Endpoint endpoint = Endpoint.create(implementor);
    endpoint.publish(value);
    
    Object client = webServiceCoercion.coerce(type.getComponentCoercionTypes()[0], value);
    return new WebServiceEndpoint(endpoint, client);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return WebServiceEndpoint.class.isAssignableFrom(target.getType());
  }

}
