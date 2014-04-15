package net.stickycode.rs.coercion.jersey;

import javax.ws.rs.Path;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

@SuppressWarnings("serial")
@Failure(FailureClassification.ServerFailure)
public class PathsMustStartWithSlashFailure
    extends RuntimeException {

  private String path;

  public PathsMustStartWithSlashFailure() {
  }

  public PathsMustStartWithSlashFailure(Path annotation) {
    super.fillInStackTrace();
    this.path = annotation.value();
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }

  @Override
  public String getMessage() {
    return String.format("Path value should start with a slash found %s", path);
  }

}
