/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
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
package net.stickycode.scheduled;

import java.lang.reflect.Method;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfiguredConfiguration;
import net.stickycode.reflector.Reflector;
import net.stickycode.stereotype.Scheduled;
import net.stickycode.stereotype.StickyComponent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

@StickyComponent
public class ScheduledBeanPostProcessor
    extends InstantiationAwareBeanPostProcessorAdapter {

  @Inject
  private ConfigurationRepository configurationRepository;
  
  @Inject
  private SchedulingSystem schedulingSystem;

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    if (typeHasSchedules(bean.getClass())) {
      ConfiguredConfiguration configuration = new ConfiguredConfiguration(bean);
      new Reflector()
          .forEachMethod(new ScheduledMethodProcessor(schedulingSystem, configuration))
          .process(bean);
      configurationRepository.register(configuration);
    }
    return true;
  }

  private boolean typeHasSchedules(Class<?> type) {
    for (Method method: type.getDeclaredMethods())
      if (method.isAnnotationPresent(Scheduled.class))
        return true;

    return false;
  }

}
