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
import java.util.Map;

import net.stickycode.stereotype.StickyFramework;

import com.google.inject.Singleton;

import de.devsurf.injection.guice.install.InstallationContext.BindingStage;

@Singleton
public class StickyFrameworkStereotypeScannerFeature
    extends StickyStereotypeScannerFeature {
  
  @Override
  public BindingStage accept(Class<Object> annotatedClass, Map<String, Annotation> annotations) {
    if (!annotatedClass.isAnnotationPresent(StickyFramework.class))
      return BindingStage.IGNORE;

    if (annotatedClass.isAnnotationPresent(getComponentAnnotation()))
      return deriveStage(annotatedClass);

    for (Annotation annotation : annotatedClass.getAnnotations()) {
      if (annotation.annotationType().isAnnotationPresent(getComponentAnnotation()))
        return deriveStage(annotatedClass);
    }

    return BindingStage.IGNORE;
  }
 
}
