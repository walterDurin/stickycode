package net.stickycode.configured.placeholder;

public class NoPlaceholder
    implements Placeholder {

  @Override
  public boolean notFound() {
    return true;
  }

  @Override
  public String getKey() {
    throw new UnsupportedOperationException("No placeholder means no key");
  }

  @Override
  public String replace(String lookup) {
    throw new UnsupportedOperationException("No placeholder means replacement");
  }
}