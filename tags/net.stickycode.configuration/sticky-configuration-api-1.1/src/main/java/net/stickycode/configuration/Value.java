package net.stickycode.configuration;

public interface Value {

  String get();

  boolean hasPrecedence(Value v);

}
