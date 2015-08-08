package net.stickycode.stereotype.failure;

public enum FailureClassification {
  NotFound,
  NotAuthenticated,
  NotAuthorised,
  ServerFailure,
  InvalidRequest,
  TemporarilyUnavailable,
  ResourceExhaustion,
  ConnectionFailure,
  Timeout,
  Undefined,
  GatewayFailure;
}
