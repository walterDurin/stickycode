package net.stickycode.stereotype.primitive;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class DataVolumesMustBeNaturalNumbersException
    extends PermanentException {

  public DataVolumesMustBeNaturalNumbersException(String value) {
    super("Found '', but Data volumes must be natural numbers with units e.g. 1g, 125m, 1032k, 125M, 123mB, 1234mb, 100KB, 100kb", value);
  }

}
