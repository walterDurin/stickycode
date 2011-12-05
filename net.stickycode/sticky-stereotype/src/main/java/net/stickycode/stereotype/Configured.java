package net.stickycode.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * Metadata to mark a field as requiring injection of configuration during application construction. The whole idea being that
 * there is no need for boilerplate configuration loading in the code, you just say a field needs to be injected and assume it will be.
 * </p>
 * <h3>Testing like it runs</h3>
 * <p>
 * It is useful to use a tool like Mockwire to isolate your test contexts and use the same configuration mechanisms such that you are testing
 * you code like its going to run in production.
 * </p>
 * <h3>Configuration mapping</h3>
 * <p>After many attempts I've settled on not allowing keys to be defined in the code, the names of configuration elements depends on the injection system
 * used but will always be determinable which is the important thing. Allowing keys and default values to be defined on the annotation just leave you back
 * to a position where boilterplate is turning up in your code. If many classes shared the same configuragion then the configuration system should define that not the
 * class being configured.
 * <p>
 * <h3>Examples</h3>
 * <p>
 * Using classpath scanning with spring for example you would get a component <b>configuredBean</b> will be initialised with values from keys
 * <b>configuredBean.password</b>, <b>configuredBean.user</b> and <b>configuredBean.url</b> where url is coerced appropriately.
 * </p>
 *
 * <pre>
 * &#064;StickyComponent
 * public ConfiguredBean {
 *
 *   &#064;Configured(secret=true)
 *   private String password;
 *
 *   &#064;Configured
 *   private String user;
 *
 *   &#064;Configured("The url for accessing some service")
 *   private URL url;
 *
 * }
 * </pre>
 *
 * <h3>Mandatory configuration</h3>
 * <p>
 * For situations where a reasonable default can be defined at coding time, just set the value as you normally would in java code. This means unit testing
 * works without intervention. The only downside of this is that all {@link Configured} fields must be objects not primitive as there is no way to determine if
 * its actually been defaulted, I feel thats well worth it for being able to understand the configuration of any given context.
 * </p>
 * <p>In this example there is a reasonable default for the theme because its packaged with the bean but can be overridden by configuration.
 * Note that the Boolean field is not primitive, if it was will get an error at application construction as the default nature of the field could not be determined.
 * <pre>
 * &#064;StickyComponent
 * public ThemedWebsiteWithSecureLoginBean {
 *
 *   &#064;Configured
 *   private String defaultTheme = "butterfly";
 *
 *   &#064;Configured
 *   private Boolean allowAnonymousLoging = true;
 * }
 * </pre>
 *
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configured {

  /**
   * Don't log the value as its security sensitive information (default false)
   */
  boolean secret() default false;

  /**
   * @see {@link Configured#value()}
   * A brief description of the purpose of the configuration
   */
  @Deprecated
  String description() default "";

  /**
   * Describe the configuration such that someone reading this message could provide appropriate configuration
   */
  String value() default "";

}
