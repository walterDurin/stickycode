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
package net.stickycode.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflector {

  private List<FieldProcessor> fieldProcessors;
  private List<MethodProcessor> methodProcessors;

  public Reflector() {
  }

  public Reflector forEachField(FieldProcessor... processors) {
    if (fieldProcessors == null)
      fieldProcessors = Arrays.asList(processors);
    else
      for (FieldProcessor fieldProcessor : processors)
        fieldProcessors.add(fieldProcessor);

    return this;
  }

  public Reflector forEachMethod(MethodProcessor... processors) {
    if (methodProcessors == null)
      methodProcessors = Arrays.asList(processors);
    else
      for (MethodProcessor methodProcessor : processors) {
        methodProcessors.add(methodProcessor);
      }

    return this;
  }

  public void process(Class<?> type) {
    List<Method> methods = new ArrayList<Method>();

    while (type != Object.class) {
      if (fieldProcessors != null)
        processFields(null, type);

      if (methodProcessors != null)
        collectMethods(methods, null, type);
      type = type.getSuperclass();
    }

    if (methodProcessors != null)
      processMethods(methods, null);
  }

  public void process(Object target) {
    List<Method> methods = new ArrayList<Method>();

    Class<?> type = target.getClass();
    while (type != Object.class) {
      if (fieldProcessors != null)
        processFields(target, type);

      if (methodProcessors != null)
        collectMethods(methods, target, type);

      type = type.getSuperclass();
    }

    if (methodProcessors != null)
      processMethods(methods, target);
  }

  private void processMethods(List<Method> methods, Object target) {
    for (MethodProcessor processor : methodProcessors)
      processor.sort(methods);

    for (MethodProcessor processor : methodProcessors)
      for (Method method : methods)
        processor.processMethod(target, method);
  }

  private void collectMethods(List<Method> methods, Object target, Class<?> type) {
    for (Method method : type.getDeclaredMethods())
      if (canProcess(method))
        methods.add(method);
  }

  private boolean canProcess(Method method) {
    for (MethodProcessor processor : methodProcessors)
      if (processor.canProcess(method))
        return true;

    return false;
  }

  private void processFields(Object target, Class<?> type) {
    for (FieldProcessor processor : fieldProcessors) {
      Field[] fields = type.getDeclaredFields();
      for (Field field : fields)
        if (processor.canProcess(field))
          processor.processField(target, field);
    }
  }

}
