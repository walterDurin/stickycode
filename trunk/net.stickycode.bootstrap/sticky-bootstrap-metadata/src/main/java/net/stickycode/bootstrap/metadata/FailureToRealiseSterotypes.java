package net.stickycode.bootstrap.metadata;

import java.util.Set;

import net.stickycode.stereotype.failure.ParameterisedFailure;

@SuppressWarnings("serial")
public class FailureToRealiseSterotypes
    extends ParameterisedFailure {

  public FailureToRealiseSterotypes(Set<String> missing) {
    super("Failed to load stereotypes {}", missing);
  }

}
