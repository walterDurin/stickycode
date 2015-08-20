package net.stickycode.stereotype.failure.resolver;


public class ParameterFragment
    extends Fragment {

  @Override
  public boolean isParameter() {
    return true;
  }

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    return (getClass() == obj.getClass());
  }
}
