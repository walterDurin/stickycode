package net.stickycode.component.spring25;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import net.stickycode.stereotype.StickyComponent;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

@StickyComponent
public class InjectAnnotationBeanPostProcessor
    extends AutowiredAnnotationBeanPostProcessor {

  @Override
  protected Class<? extends Annotation> getAutowiredAnnotationType() {
    return Inject.class;
  }

}
