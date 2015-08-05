package net.stickycode.exception.resolver;

public class QuoteFragment
    extends Fragment {

  @Override
  public boolean isQuote() {
    return true;
  }

  @Override
  public int hashCode() {
    return 2;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    return (getClass() == obj.getClass());
  }
  
}
