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
package net.stickycode.configured.spring4;

import javax.inject.Inject;

import net.stickycode.configured.ConfiguredBeanProcessor;
import net.stickycode.metadata.MetadataResolverRegistry;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.configured.ConfiguredStrategy;
import net.stickycode.stereotype.configured.PostConfigured;
import net.stickycode.stereotype.configured.PreConfigured;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

@StickyComponent
public class ConfiguredBeanPostProcessor
    extends InstantiationAwareBeanPostProcessorAdapter {

  @Inject
  private ConfiguredBeanProcessor processor;

  @Inject
  MetadataResolverRegistry metdataResolverRegistry;

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    if (typeIsConfigured(bean.getClass())) {
      processor.process(bean);
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
