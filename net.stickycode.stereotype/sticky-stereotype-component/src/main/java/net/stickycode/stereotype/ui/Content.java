package net.stickycode.stereotype.ui;

/**
 * Contract for separating the configuration of content from its use.
 * 
 * <h3>Developer</h3>
 * 
 * A developer just wants to stereotype a piece of content as being external and let the system work out how to fill it in.
 * For using sticky content there are two things you need to do, mark it with {@link ConfiguredContent} and set the type to be
 * Content.
 * 
 * If String was not final we could use a proxy to resolve the localisation as needed. So we have this interface instead.
 * 
 * <h3>Business</h3>
 * 
 * Will define the content is some way, either cms or flat file or asking developers to do it ;-). Once the developers have
 * annotated the content fields of the application you can generate the manifest of the content they should provide.
 * 
 * <pre>
 *  &#064;StickyPresenter
 *  public class Display {
 *    &#064;Configured
 *    private numberOfFailuredBeforeWarning = 3;
 *    
 *    &#064;ConfiguredContent
 *    Content tooManyFailuresWarning;
 *    
 *    &#064;ConfiguredContent
 *    String titleMessage;
 *    
 *    &#064;ConfiguredContent
 *    String titleMessageWithDefault = "Some default text";
 *  }
 * </pre>
 */
public interface Content {

  /**
   * Return a localised string suitable for representation to a user
   */
  String get();

  /**
   * Return the same thing as {@link #get()}, just means you know if you log or use content in a string context it behave somewhat
   * like a string.
   */
  String toString();
  
  // I wondered about getLocale here but could not see a reason why you would want it, when you would have a 
  // Locale Provider defined in you application content anyway
}
