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
package net.stickycode.scheduled;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.stickycode.configured.ConfiguredBeanProcessor;
import net.stickycode.reflector.MethodProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledMethodProcessor
    implements MethodProcessor {

  private Logger log = LoggerFactory.getLogger(ScheduledMethodProcessor.class);

  private ScheduledRunnableRepository schedulingSystem;

  private ConfiguredBeanProcessor beanProcessor;

  private List<ScheduledMethodInvokerFactory> methodInvokerFactories = new ArrayList<ScheduledMethodInvokerFactory>();

  public ScheduledMethodProcessor() {
  }

  @Override
  public void processMethod(Object target, Method method) {
    ScheduledRunnable scheduledMethodInvoker = createScheduledMethodInvoker(target, method);
    beanProcessor.process(scheduledMethodInvoker,
        Introspector.decapitalize(target.getClass().getSimpleName()) + "." + method.getName(), null);
    log.debug("Found {} to register for scheduling", scheduledMethodInvoker);
    schedulingSystem.schedule(scheduledMethodInvoker);
  }

  private ScheduledRunnable createScheduledMethodInvoker(Object target, Method method) {
    for (ScheduledMethodInvokerFactory factory : methodInvokerFactories) {
      if (factory.canInvoke(method))
        return factory.create(target, method);
    }

    throw new FactoryNotRegisteredToProcessMethodException(target, method, methodInvokerFactories);
  }

  public ScheduledMethodProcessor withInvokers(Set<ScheduledMethodInvokerFactory> factories) {
    methodInvokerFactories.addAll(factories);
    return this;
  }

  @Override
  public boolean canProcess(Method method) {
    for (ScheduledMethodInvokerFactory factory : methodInvokerFactories) {
      if (factory.canInvoke(method))
        return true;
    }

    return false;
  }

  @Override
  public void sort(List<Method> methods) {
  }

  public ScheduledMethodProcessor withSchedulingSystem(ScheduledRunnableRepository schedulingSystem) {
    this.schedulingSystem = schedulingSystem;
    return this;
  }

  public ScheduledMethodProcessor withConfiguration(ConfiguredBeanProcessor configuration) {
    this.beanProcessor = configuration;
    return this;
  }

}
