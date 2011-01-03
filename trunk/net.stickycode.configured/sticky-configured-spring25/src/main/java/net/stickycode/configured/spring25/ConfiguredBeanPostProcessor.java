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
package net.stickycode.configured.spring25;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.reflector.Reflector;
import net.stickycode.stereotype.StickyComponent;

/**
 * Registers fields marked as configured for configuration
 */
@StickyComponent
public class ConfiguredBeanPostProcessor
    extends InstantiationAwareBeanPostProcessorAdapter {

  @Inject
  private ConfigurationSystem configuration;

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    new Reflector()
        .forEachField(new ConfiguredFieldProcessor(configuration))
        .process(bean);
    return true;
  }

  public void setConfiguration(ConfigurationSystem configuration) {
    this.configuration = configuration;
  }

}
