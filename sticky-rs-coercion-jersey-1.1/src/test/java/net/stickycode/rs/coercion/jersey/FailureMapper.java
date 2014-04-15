package net.stickycode.rs.coercion.jersey;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import net.stickycode.rs.coercion.jersey.FailureClassificationMapper;
import net.stickycode.stereotype.failure.Failure;

@Provider
public class FailureMapper
    implements ExceptionMapper<RuntimeException> {

  @Override
  public Response toResponse(RuntimeException exception) {
    Failure failure = exception.getClass().getAnnotation(Failure.class);
    if (failure == null)
      return null;

    int status = new FailureClassificationMapper().resolveClassification(failure.value());
    return Response
        .status(status)
        .header("X-Failure-Code", exception.getClass().getSimpleName())
        .build();
  }

}
