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
package net.stickycode.cxf.guice3;

import javax.inject.Inject;
import javax.jws.WebService;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

@StickyComponent
@StickyFramework
public class WebServiceTypeListener
    implements TypeListener {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private WebServiceCollector collector;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (isWebService(type)) {
      if (collector == null)
        throw new AssertionError("on hearing " + type.getRawType().getName() + " found that " + getClass().getSimpleName() + " was not injected with a " + WebServiceCollector.class.getSimpleName());
      
      encounter.register(collector);
      log.debug("encountering {} registering injector {}", type, collector);
    }
  }

  private <I> boolean isWebService(TypeLiteral<I> type) {
    for (Class<?> i : type.getRawType().getInterfaces())
      if (i.isAnnotationPresent(WebService.class))
        return true;

    return false;
  }
}
