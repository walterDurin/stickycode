package net.stickycode.component.cxf.spring3;

import javax.inject.Inject;
import javax.jws.WebService;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.ws.cxf.WebServiceExposureRepository;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@StickyComponent
public class WebServiceCollector
    implements BeanPostProcessor {

  @Inject
  private WebServiceExposureRepository exposures;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    process(bean, beanName);
    return bean;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  void process(Object bean, String beanName) {
    for (Class<?> i : bean.getClass().getInterfaces())
      if (i.isAnnotationPresent(WebService.class))
        exposures.add(bean, i);
  }

}
