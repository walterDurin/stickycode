package net.stickycode.exception.resolver;

public abstract class Fragment {

  public boolean isParameter() {
    return false;
  }

  public boolean isQuote() {
    return false;
  }

  public String getValue() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  public boolean isText() {
    return false;
  }

}
