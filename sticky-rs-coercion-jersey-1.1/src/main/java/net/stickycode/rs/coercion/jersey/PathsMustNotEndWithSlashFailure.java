package net.stickycode.rs.coercion.jersey;

import javax.ws.rs.Path;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

@SuppressWarnings("serial")
@Failure(FailureClassification.ServerFailure)
public class PathsMustNotEndWithSlashFailure
    extends RuntimeException {

  private String path;

  public PathsMustNotEndWithSlashFailure() {
  }

  public PathsMustNotEndWithSlashFailure(Path annotation) {
    super.fillInStackTrace();
    this.path = annotation.value();
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }

  @Override
  public String getMessage() {
    return String.format("Path value should not end in a slash found %s", path);
  }

}
