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
package net.stickycode.configured.spring3;

import net.stickycode.configured.AbstractPrimitiveConfiguratedTest;
import net.stickycode.stereotype.StickyComponent;
import net.stickycode.stereotype.StickyPlugin;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class PrimitiveHaveNoDefaultsTest
    extends AbstractPrimitiveConfiguratedTest {

  protected void configure(Object instance) {
    GenericApplicationContext c = new GenericApplicationContext();

    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(c, false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyComponent.class));
    scanner.addIncludeFilter(new AnnotationTypeFilter(StickyPlugin.class));
    scanner.scan("net.stickycode");

    c.refresh();

    c.getAutowireCapableBeanFactory().autowireBean(instance);
    c.getAutowireCapableBeanFactory().autowireBean(this);
  }

}
