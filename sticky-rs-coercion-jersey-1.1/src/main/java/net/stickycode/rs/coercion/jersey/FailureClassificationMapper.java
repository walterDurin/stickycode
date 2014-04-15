package net.stickycode.rs.coercion.jersey;

import net.stickycode.stereotype.failure.FailureClassification;

public class FailureClassificationMapper {

  int resolveClassification(FailureClassification classification) {
    switch (classification) {
    case InvalidRequest:
      return 400;

    case NotFound:
      return 404;

    case ServerFailure:
      return 500;

    case GatewayFailure:
      return 502;

    case NotAuthenticated:
      return 401;

    case NotAuthorised:
      return 403;

    case TemporarilyUnavailable:
      return 503;

    case Undefined:
      return 500;

    }

    throw new RuntimeException("Unmapped classification " + classification);
  }

  FailureClassification resolveClassification(int status) {
    switch (status) {
    case 400:
      return FailureClassification.InvalidRequest;

    case 404:
      return FailureClassification.NotFound;

    case 500:
      return FailureClassification.ServerFailure;

    case 502:
      return FailureClassification.GatewayFailure;

    case 503:
      return FailureClassification.TemporarilyUnavailable;
    }

    return FailureClassification.Undefined;
  }

}
