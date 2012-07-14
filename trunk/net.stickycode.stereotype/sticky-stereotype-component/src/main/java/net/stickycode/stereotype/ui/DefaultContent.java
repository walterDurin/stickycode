package net.stickycode.stereotype.ui;

/**
 * A static default value for localised content
 */
public class DefaultContent
    implements Content {

  private final String value;

  public DefaultContent(String value) {
    super();
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

}
