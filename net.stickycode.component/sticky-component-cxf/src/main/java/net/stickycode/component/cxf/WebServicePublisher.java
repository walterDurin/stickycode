package net.stickycode.component.cxf;

import java.util.logging.LogManager;

import javax.inject.Inject;
import javax.jws.WebService;

import net.stickycode.stereotype.StickyComponent;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapBindingConstants;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@StickyComponent
public class WebServicePublisher
    implements BeanPostProcessor {

  static {
    java.util.logging.Logger util = LogManager.getLogManager().getLogger("");
    for (java.util.logging.Handler handler : util.getHandlers())
      util.removeHandler(handler);
    SLF4JBridgeHandler.install();
  }

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  Bus bus;

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
        publish(bean, beanName, i);
  }

  private void publish(Object bean, String beanName, Class<?> i) {
    log.info("Publishing {} as web service {}", beanName, i.getSimpleName());
    String address = "/" + i.getSimpleName() + getLeaf(bean.getClass().getPackage());
    JaxWsServerFactoryBean factory = createServerFactory();
    factory.setServiceClass(i);
    new EndpointImpl(bus, bean, factory).publish(address);
  }

  protected JaxWsServerFactoryBean createServerFactory() {
    JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
    serverFactory.setBus(bus);
    serverFactory.setServiceFactory(createServiceFactory());
    serverFactory.setBindingId(SoapBindingConstants.SOAP11_BINDING_ID);
    return serverFactory;
  }

  protected JaxWsServiceFactoryBean createServiceFactory() {
    JaxWsServiceFactoryBean s = new JaxWsServiceFactoryBean();
    s.setBus(bus);
    return s;
  }

  private String getLeaf(Package p) {
    String name = p.getName();
    String version = name.substring(name.lastIndexOf('.') + 1);
    if (version.matches("^v[\\.0-9]+$"))
      return "/" + version;

    throw new WebServiceShouldExistInVersionedPackageException(p);
  }

}
