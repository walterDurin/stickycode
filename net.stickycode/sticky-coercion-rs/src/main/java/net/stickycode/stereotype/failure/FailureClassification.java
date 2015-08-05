package net.stickycode.stereotype.failure;

public enum FailureClassification {
  NotFound,
  NotAuthenticated,
  NotAuthorised,
  ServerFailure,
  InvalidRequest,
  TemporarilyUnavailable,
  Undefined,
  GatewayFailure;
}
