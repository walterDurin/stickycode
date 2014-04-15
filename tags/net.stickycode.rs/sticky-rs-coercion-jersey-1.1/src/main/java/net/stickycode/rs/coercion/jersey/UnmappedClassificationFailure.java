package net.stickycode.rs.coercion.jersey;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

@SuppressWarnings("serial")
@Failure(FailureClassification.ServerFailure)
public class UnmappedClassificationFailure
    extends RuntimeException {

  private FailureClassification classification;

  public UnmappedClassificationFailure() {
    super();
  }

  public UnmappedClassificationFailure(FailureClassification classification) {
    super();
    this.classification = classification;
    super.fillInStackTrace();
  }

  @Override
  public String getMessage() {
    return String.format("Classification %s was not mapped", classification);
  }

  public FailureClassification getClassification() {
    return classification;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
