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

import java.lang.reflect.Field;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.reflector.AnnotatedFieldProcessor;
import net.stickycode.stereotype.Configured;


public class ConfiguredFieldProcessor
    extends AnnotatedFieldProcessor {

  private final ConfigurationSystem configuration;

  public ConfiguredFieldProcessor(ConfigurationSystem configuration) {
    super(Configured.class);
    this.configuration = configuration;
  }

  @Override
  public void processField(Object target, Field field) {
    if (field.getType().isPrimitive())
      throw new ConfiguredFieldsMustNotBePrimitiveAsDefaultDerivationIsImpossible(target, field);

    configuration.registerField(target, field);
  }

}
