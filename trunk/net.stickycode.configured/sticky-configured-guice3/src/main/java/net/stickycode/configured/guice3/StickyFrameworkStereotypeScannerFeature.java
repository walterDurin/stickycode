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
package net.stickycode.configured.guice3;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;

import com.google.inject.MembersInjector;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeListener;

import de.devsurf.injection.guice.install.InstallationContext.BindingStage;
import de.devsurf.injection.guice.scanner.features.BindingScannerFeature;

@Singleton
public class StickyFrameworkStereotypeScannerFeature
    extends BindingScannerFeature {

  @Override
  public BindingStage accept(Class<Object> annotatedClass, Map<String, Annotation> annotations) {
    if (!annotatedClass.isAnnotationPresent(StickyFramework.class))
      return BindingStage.IGNORE;
    
    if (annotatedClass.isAnnotationPresent(StickyComponent.class))
      return BindingStage.BUILD;

    for (Annotation annotation : annotatedClass.getAnnotations()) {
      if (annotation.annotationType().isAnnotationPresent(StickyComponent.class))
        return BindingStage.BUILD;
    }

    return BindingStage.IGNORE;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void process(Class<Object> annotatedClass, Map<String, Annotation> annotations) {
    System.out.println(annotatedClass.getName());
    Class<Object>[] interfaces = (Class<Object>[]) annotatedClass.getInterfaces();

    if (interfaces.length == 0) {
      List<Class<?>> interfaceCollection = new ArrayList<Class<?>>();
      Class<? super Object> parent = annotatedClass.getSuperclass();
      while (parent != null && !parent.equals(Object.class)) {
        Collections.addAll(interfaceCollection, parent.getInterfaces());
        parent = parent.getSuperclass();
      }
      interfaces = interfaceCollection.toArray(new Class[interfaceCollection.size()]);
    }
    if (interfaces.length == 0) {
      bind(annotatedClass, null, Scopes.SINGLETON);
    }
    for (Class<Object> interf : interfaces) {
      if (interf.isAssignableFrom(MembersInjector.class))
        bind(annotatedClass, null, Scopes.SINGLETON);
      else
        if (interf.isAssignableFrom(TypeListener.class))
          bindListener(annotatedClass);
        else
          bind(annotatedClass, interf, (Annotation) null, Scopes.SINGLETON);
    }
  }

  private void bindListener(Class<Object> annotatedClass) {
    TypeListener typeListener = typeListener(annotatedClass);
    _binder.requestInjection(typeListener);
    _binder.bindListener(Matchers.any(), typeListener);
  }

  private TypeListener typeListener(Class<Object> annotatedClass) {
    try {
      return (TypeListener) annotatedClass.newInstance();
    }
    catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
