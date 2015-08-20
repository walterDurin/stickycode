package net.stickycode.stereotype.failure;

public enum FailureClassification {
  /**
   * The is an issue with the application container
   */
  Container,
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
