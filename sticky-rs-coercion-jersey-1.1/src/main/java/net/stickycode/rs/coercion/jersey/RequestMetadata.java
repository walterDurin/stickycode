package net.stickycode.rs.coercion.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class RequestMetadata {

  private Method method;

  private Object[] args;

  private HttpMethod httpMethod;

  public RequestMetadata(Method method, Object[] args) {
    this.method = method;
    this.args = args;
    processAnnotations();
  }

  public String getOutgoingMediaType() {
    Consumes annotation = method.getAnnotation(Consumes.class);

    if (annotation == null)
      return MediaType.APPLICATION_JSON;

    // XXX better understanding of parameters means we can choose a better media type, this will work for now
    return annotation.value()[0];
  }

  public String getIngoingMediaType() {
    Produces annotation = method.getAnnotation(Produces.class);

    if (annotation == null)
      return MediaType.APPLICATION_JSON;

    // XXX better understanding of parameters means we can choose a better media type, this will work for now
    return annotation.value()[0];
  }

  public String getOperationPath() {
    String path = annotatedPath(method.getDeclaringClass());
    if (path.length() == 0)
      return resolvePathParams(annotatedPath(method));

    return resolvePathParams(path + annotatedPath(method));
  }

  private String annotatedPath(AnnotatedElement element) {
    Path annotation = element.getAnnotation(Path.class);
    if (annotation == null)
      return "";

    String value = annotation.value();
    if (value.charAt(value.length() - 1) == '/')
      throw new PathsMustNotEndWithSlashFailure(annotation);

    if (value.charAt(0) != '/')
      throw new PathsMustStartWithSlashFailure(annotation);

    return value;
  }

  private String resolvePathParams(String path) {
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    for (int i = 0; i < parameterAnnotations.length; i++) {
      for (Annotation a : parameterAnnotations[i]) {
        if (a instanceof PathParam) {
          String propertyName = ((PathParam) a).value();
          path = resolveValue(path, propertyName, args[i]);
        }
      }
    }

    return path;
  }

  private String resolveValue(String path, String propertyName, Object object) {
    return path.replaceAll("\\{" + propertyName + "\\}", object.toString());
  }

  public boolean hasPayload() {
    if (method.isAnnotationPresent(POST.class))
      return true;

    if (method.isAnnotationPresent(PUT.class))
      return true;

    return false;
  }

  void processAnnotations() {
    for (Annotation a : method.getAnnotations()) {
      HttpMethod m = a.annotationType().getAnnotation(HttpMethod.class);
      if (m != null)
        httpMethod = m;
    }

    if (httpMethod == null)
      throw new UnsupportedOperationException("Operation " + method.getName()
          + " not supported as its not meta annotated with HttpMethod");
  }

  public Object getPayload() {
    return args[args.length - 1];
  }

  public String getMethod() {
    return httpMethod.value();
  }

}
