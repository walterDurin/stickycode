package net.stickycode.rs.coercion.jersey;

import java.lang.reflect.Proxy;

import javax.ws.rs.Path;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class RestfulCoercion
    extends AbstractNoDefaultCoercion<Object> {

  @Override
  public Object coerce(CoercionTarget target, String value) {

    return Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[] { target.getType() },
        new JerseyClientProxy(target.getType(), value));
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    if (!target.getType().isInterface())
      return false;

    return target.getAnnotatedElement().isAnnotationPresent(Path.class);
  }

}
