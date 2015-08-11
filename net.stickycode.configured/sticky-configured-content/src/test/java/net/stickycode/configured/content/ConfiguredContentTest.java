package net.stickycode.configured.content;

import net.stickycode.stereotype.content.ConfiguredContent;
import net.stickycode.stereotype.content.Content;



public class ConfiguredContentTest {

 @ConfiguredContent
 String some;

 @ConfiguredContent
 String withDefault = "A default value";

 @ConfiguredContent
 Content content;

}
