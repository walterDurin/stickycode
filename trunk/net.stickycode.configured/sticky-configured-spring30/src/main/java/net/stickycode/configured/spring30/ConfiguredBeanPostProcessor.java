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
package net.stickycode.configured.spring30;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfiguredConfiguration;
import net.stickycode.configured.ConfiguredFieldProcessor;
import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.reflector.Reflector;
import net.stickycode.stereotype.Configured;
import net.stickycode.stereotype.ConfiguredStrategy;
import net.stickycode.stereotype.PostConfigured;
import net.stickycode.stereotype.PreConfigured;
import net.stickycode.stereotype.StickyComponent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

@StickyComponent
public class ConfiguredBeanPostProcessor
    extends InstantiationAwareBeanPostProcessorAdapter {

  @Inject
  private ConfigurationRepository configurationRepository;

  @Inject
  MetadataResolverRegistry metdataResolverRegistry;

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    if (typeIsConfigured(bean.getClass())) {
      ConfiguredConfiguration configuration = new ConfiguredConfiguration(bean);
      new Reflector()
          .forEachField(new ConfiguredFieldProcessor(configuration))
          .process(bean);
      configurationRepository.register(configuration);
    }
    return true;
  }

  private boolean typeIsConfigured(Class<?> type) {
    if (metdataResolverRegistry
        .does(type)
        .haveAnyFieldsMetaAnnotatedWith(Configured.class, ConfiguredStrategy.class))
      return true;

    if (metdataResolverRegistry
        .does(type)
        .haveAnyMethodsMetaAnnotatedWith(PreConfigured.class, PostConfigured.class))
      return true;


    return false;
  }

}