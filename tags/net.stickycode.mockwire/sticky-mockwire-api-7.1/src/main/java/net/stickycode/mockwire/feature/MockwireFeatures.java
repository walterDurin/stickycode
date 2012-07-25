package net.stickycode.mockwire.feature;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface MockwireFeatures {

  Class<? extends Annotation>[] value();
}
