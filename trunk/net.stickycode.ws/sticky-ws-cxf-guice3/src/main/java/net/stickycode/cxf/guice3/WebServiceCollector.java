/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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
package net.stickycode.cxf.guice3;

import javax.inject.Inject;
import javax.jws.WebService;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.ws.cxf.WebServiceExposureRepository;

import com.google.inject.spi.InjectionListener;

@StickyComponent
@StickyFramework
public class WebServiceCollector
    implements InjectionListener<Object> {

  @Inject
  private WebServiceExposureRepository exposures;

  void process(Object bean) {
    for (Class<?> i : bean.getClass().getInterfaces())
      if (i.isAnnotationPresent(WebService.class))
        exposures.add(bean, i);
  }

  @Override
  public void afterInjection(Object instance) {
    process(instance);
  }

}
