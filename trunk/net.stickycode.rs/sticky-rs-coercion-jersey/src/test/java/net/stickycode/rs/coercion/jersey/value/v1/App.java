package net.stickycode.rs.coercion.jersey.value.v1;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class App
    extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<Class<?>>();
    set.add(JacksonJsonProvider.class);
    return set;
  }
  
  @Override
  public Set<Object> getSingletons() {
    Set<Object> set = new HashSet<Object>();
    set.add(new Concrete());
    return set;
  }

}