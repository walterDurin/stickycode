/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.coercion.ws;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.stereotype.StickyPlugin;
import net.stickycode.stereotype.component.StickyMapper;

@StickyPlugin
@StickyFramework
public class WebServiceCoercion
    implements Coercion<Object> {

  @Override
  public Object coerce(CoercionTarget type, String value) {
    URL wsdlDocumentLocation = createWsdlUrl(value);
    WebService annotation = type.getType().getAnnotation(WebService.class);
    String namespace = deriveNamespace(type.getType(), annotation);
    String serviceName = deriveServiceName(annotation, type.getType());
    try {
      Service service = Service.create(wsdlDocumentLocation, new QName(namespace, serviceName));
      return service.getPort(type.getType());
    }
    catch (Throwable t) {
      mapException(wsdlDocumentLocation, annotation, t);
      throw new CouldNotCreateServiceProxyException(t, wsdlDocumentLocation, annotation);
    }
  }

  private void mapException(URL wsdlDocumentLocation, WebService annotation, Throwable t) {
    if (t.getCause() != null) {
      if (t.getCause() instanceof UnknownHostException)
        throw new CouldNotConnectToWebServiceException(t, wsdlDocumentLocation, annotation);
      
      if (t.getCause() instanceof FileNotFoundException)
        throw new CouldNotConnectToWebServiceException(t, wsdlDocumentLocation, annotation);
      
      if (t.getCause() instanceof ConnectException)
        throw new CouldNotConnectToWebServiceException(t, wsdlDocumentLocation, annotation);
    }
    if (t.getMessage() != null && t.getMessage().contains("Inaccessible")) {
      throw new CouldNotConnectToWebServiceException(t, wsdlDocumentLocation, annotation);
    }
  }

  private URL createWsdlUrl(String value) {
    if (value.length() == 0)
      throw new UnparseableUrlForWebServiceException(value);
    
    try {
      return new URL(value+ "?WSDL");
    }
    catch (MalformedURLException e) {
      throw new UnparseableUrlForWebServiceException(e, value);
    }
  }

  private String deriveServiceName(WebService annotation, Class<?> webServiceClass) {
    String serviceName = annotation.serviceName();
    if (serviceName.length() == 0)
      return webServiceClass.getSimpleName() + "Service";

    return serviceName;
  }

  private String deriveNamespace(Class<?> type, WebService annotation) {
    String targetNamespace = annotation.targetNamespace();
    if (targetNamespace.length() == 0)
      return "http://" + reverse(type) + "/";

    return targetNamespace;
  }

  private String reverse(Class<?> type) {
    return joinReversed(type.getPackage().getName().split("\\."));
  }

  private String joinReversed(String[] split) {
    StringBuilder b = new StringBuilder();
    for (int i = split.length - 1; i > 0; i--)
      b.append(split[i]).append('.');

    return b.append(split[0]).toString();
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return (type.getType().isAnnotationPresent(WebService.class));
  }

}