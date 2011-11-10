package net.stickycode.configured.content;

import net.stickycode.stereotype.ui.ConfiguredContent;
import net.stickycode.stereotype.ui.Content;


public class AbstractLocalisedContentComponentTest {
  public class ConfiguredTestObject {

    @ConfiguredContent
    String direct;

    @ConfiguredContent
    Content numbers;
  }
}
