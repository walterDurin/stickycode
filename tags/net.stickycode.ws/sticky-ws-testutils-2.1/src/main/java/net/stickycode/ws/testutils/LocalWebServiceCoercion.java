package net.stickycode.ws.testutils;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.ws.WebServiceCoercion;
import net.stickycode.configured.finder.BeanFinder;
import net.stickycode.stereotype.plugin.StickyExtension;
import net.stickycode.ws.cxf.WebServiceNamingStrategy;

@StickyExtension
public class LocalWebServiceCoercion
    extends AbstractNoDefaultCoercion<LocalWebService<?>> {

  @Inject
  private BeanFinder finder;
  
  @Inject
  private WebServiceNamingStrategy namingStrategy;

  @Inject
  private WebServiceCoercion webServiceCoercion;

  @SuppressWarnings("unchecked")
  @Override
  public LocalWebService<?> coerce(CoercionTarget type, String value) {
    if (!value.equals("local://"))
      throw new IllegalArgumentException("Expected a value of 'local://', the path will be derived");
    
    Class<?> contract = type.getComponentCoercionTypes()[0].getType();
    Object implementor = finder.find(contract);
    String address = value + "sticky/"+ namingStrategy.deriveAddress(implementor, contract);

    Endpoint endpoint = Endpoint.create(implementor);
    endpoint.publish(address);
    
    Object client = webServiceCoercion.coerce(type.getComponentCoercionTypes()[0], address);
    return new LocalWebService(endpoint, client);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return LocalWebService.class.isAssignableFrom(target.getType());
  }

}
