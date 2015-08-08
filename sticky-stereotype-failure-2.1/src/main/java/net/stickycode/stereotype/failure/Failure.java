package net.stickycode.stereotype.failure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Failure {

  /**
   * 
   */
  FailureClassification value();

  /**
   * How the failure can be resolved, this is an indication for the receiver in terms of handling the error.
   */
  FailureResolution resolution() default FailureResolution.Application;

}
