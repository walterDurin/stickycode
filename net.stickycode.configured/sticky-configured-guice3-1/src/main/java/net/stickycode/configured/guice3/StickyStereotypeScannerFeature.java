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
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.reflector.Methods;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyFramework;
import net.stickycode.stereotype.component.StickyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.MembersInjector;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeListener;

import de.devsurf.injection.guice.install.InstallationContext.BindingStage;
import de.devsurf.injection.guice.install.bindjob.BindingJob;
import de.devsurf.injection.guice.scanner.features.BindingScannerFeature;

@Singleton
public class StickyStereotypeScannerFeature
    extends BindingScannerFeature {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public BindingStage accept(Class<Object> annotatedClass, Map<String, Annotation> annotations) {
    if (annotatedClass.isAnnotationPresent(StickyFramework.class))
      return BindingStage.IGNORE;

    if (annotatedClass.isAnnotationPresent(getComponentAnnotation()))
      return deriveStage(annotatedClass);

    for (Annotation annotation : annotatedClass.getAnnotations()) {
      if (annotation.annotationType().isAnnotationPresent(getComponentAnnotation()))
        return deriveStage(annotatedClass);
    }

    return BindingStage.IGNORE;
  }

  protected Class<? extends Annotation> getComponentAnnotation() {
    return StickyComponent.class;
  }

  protected BindingStage deriveStage(Class<Object> annotatedClass) {
    BindingStage calculateStage = calculateStage(annotatedClass);
    log.debug("adding {} at {}", annotatedClass.getName(), calculateStage);
    return calculateStage;
  }

  private BindingStage calculateStage(Class<Object> annotatedClass) {
    for (Class<?> contract : annotatedClass.getInterfaces()) {
      if (contract.isAssignableFrom(ConfigurationRepository.class))
        return BindingStage.BOOT_BEFORE;

      if (contract.isAssignableFrom(MembersInjector.class))
        return BindingStage.BOOT;

      if (contract.isAssignableFrom(InjectionListener.class))
        return BindingStage.BOOT;

      if (contract.isAssignableFrom(TypeListener.class))
        return BindingStage.BOOT_POST;

      if (annotatedClass.isAnnotationPresent(StickyRepository.class))
        return BindingStage.BOOT_BEFORE;

      String packageName = contract.getName();
      if (packageName.startsWith("net.stickycode.scheduled.ScheduledRunnableRepository"))
        return BindingStage.BOOT_BEFORE;
    }

    return BindingStage.BINDING;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void process(Class<Object> annotatedClass, Map<String, Annotation> annotations) {
    List<Class<?>> interfaces = collectInterfaces(annotatedClass);

    Scope scope = deriveScope(interfaces);

    bind(annotatedClass, null, scope);

    for (Class<?> interf : interfaces) {
      if (interf.isAssignableFrom(TypeListener.class))
        bindListener(annotatedClass);
      else
        if (!interf.isAssignableFrom(MembersInjector.class))
          if (Provider.class.isAssignableFrom(interf))
            bindProviderWorkaround((Class<Object>) annotatedClass, Scopes.NO_SCOPE);
          else
            bind(annotatedClass, (Class<Object>) interf, (Annotation) null, scope);
    }

    if (!Provider.class.isAssignableFrom(annotatedClass) && !MembersInjector.class.isAssignableFrom(annotatedClass))
      for (Class<?> blah = annotatedClass; blah != null; blah = blah.getSuperclass())
        for (Type type : blah.getGenericInterfaces()) {
          if (type instanceof ParameterizedType) {
            bindParameterizedType(annotatedClass, type);
          }
        }

  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected void bindParameterizedType(Class<?> annotatedClass, Type type) {
    // if the type is parameterised and not multibound then there will only be one instance,
    // so there is no need to bind to the generic interface
  }

  private Scope deriveScope(List<Class<?>> interfaces) {
    return Scopes.SINGLETON;
  }

  /**
   * This nasty code is to workaround the bug fixed by (NOTE its says closed but its not fixed yet) in javac. Without these casts
   * javac will fail while ecj will be fine.
   */
  private void bindProviderWorkaround(Class<Object> annotatedClass, Object object) {
    Class<Object> annotatedClass2 = annotatedClass;
    if (annotatedClass2 instanceof Class)
      bindProvider((Class<? extends Provider<Object>>) (Object) annotatedClass2, null);
  }

  private List<Class<?>> collectInterfaces(Class<Object> annotatedClass) {
    List<Class<?>> interfaces = new ArrayList<Class<?>>();
    for (Class<?> base = annotatedClass; base != null; base = base.getSuperclass())
      for (Class<?> class1 : base.getInterfaces()) {
        interfaces.add(class1);
        processInterface(class1, interfaces);
      }
    
    log.debug("found {} with {}", annotatedClass, interfaces);
    return interfaces;
  }

  private <Y, T extends Provider<Y>> void bindProvider(Class<T> providerClass, Scope scope) {
    BindingJob job = new ProviderClassBindingJob(scope, providerClass.getName());
    if (!tracer.contains(job)) {
      synchronized (_binder) {
        Method m = Methods.find(providerClass, "get");
        // ParameterizedType t = Types.providerOf();
        TypeLiteral<T> tl = (TypeLiteral<T>) TypeLiteral.get(providerClass);
        ScopedBindingBuilder scopedBuilder = _binder.bind((Class<Y>) m.getReturnType())
            .toProvider(tl);
        if (scope != null) {
          scopedBuilder.in(scope);
        }
      }
      tracer.add(job);
    }
    else {
      log.info("ignoring {}", job);
    }
  }

  private void processInterface(Class<?> target, List<Class<?>> interfaces) {
    for (Class<?> class1 : target.getInterfaces()) {
      interfaces.add(class1);
      processInterface(class1, interfaces);
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
