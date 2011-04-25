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
package net.stickycode.configured.guice3;

import java.lang.reflect.Field;

import javax.inject.Inject;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import net.stickycode.stereotype.Configured;

public class ConfiguredTypeListener
    implements TypeListener {

  @Inject
  private ConfiguredInjector membersInjector;

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    if (typeIsConfigured(type))
      encounter.register(membersInjector);
  }

  private <I> boolean typeIsConfigured(TypeLiteral<I> type) {
    for (Field field : type.getRawType().getDeclaredFields())
      if (field.isAnnotationPresent(Configured.class))
        return true;

    return false;
  }
}
