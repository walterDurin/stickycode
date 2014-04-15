package net.stickycode.rs.coercion.jersey.value.v1;

import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

@SuppressWarnings("serial")
@Failure(FailureClassification.GatewayFailure)
public class GatewayFailure
    extends RuntimeException {

}
