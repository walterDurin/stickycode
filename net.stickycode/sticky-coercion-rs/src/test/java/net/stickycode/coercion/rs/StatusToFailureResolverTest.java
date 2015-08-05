package net.stickycode.coercion.rs;

import static org.assertj.core.api.Assertions.assertThat;
import net.stickycode.stereotype.failure.Failure;
import net.stickycode.stereotype.failure.FailureClassification;

import org.junit.Test;

public class StatusToFailureResolverTest {

  @SuppressWarnings("serial")
  @Failure(FailureClassification.NotFound)
  static class NotFound
      extends RuntimeException {
  }

  @SuppressWarnings("serial")
  @Failure(FailureClassification.TemporarilyUnavailable)
  static class Unavailable
      extends RuntimeException {
  }

  @Test
  public void unmapped() {
    assertThat(new FailureMetadata().resolve(FailureClassification.Undefined)).isNull();
  }

  @Test
  public void notMatched() {
    assertThat(new FailureMetadata(NotFound.class).resolve(FailureClassification.Undefined)).isNull();
  }

  @Test
  public void notFound() {
    assertThat(new FailureMetadata(NotFound.class).resolve(FailureClassification.NotFound)).isExactlyInstanceOf(NotFound.class);
  }

  @Test
  public void unavailable() {
    assertThat(new FailureMetadata(Unavailable.class).resolve(FailureClassification.TemporarilyUnavailable)).isExactlyInstanceOf(Unavailable.class);
  }
}
