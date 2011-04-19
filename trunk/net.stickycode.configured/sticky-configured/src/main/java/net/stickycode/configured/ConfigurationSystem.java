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
package net.stickycode.configured;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.CollectionCoercion;
import net.stickycode.coercion.PatternCoercion;
import net.stickycode.coercion.StringCoercion;
import net.stickycode.coercion.StringConstructorCoercion;
import net.stickycode.coercion.ValueOfMethodCoercion;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class ConfigurationSystem {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private List<ConfigurationSource> sources = new LinkedList<ConfigurationSource>();

  private List<ConfiguredField> fields = new LinkedList<ConfiguredField>();
  private final KeyGenerator keyGenerator;
  private List<Coercion<?>> coercions = new LinkedList<Coercion<?>>();

  public ConfigurationSystem() {
    this(new SimpleNameDotFieldKeyGenerator());
  }

  public ConfigurationSystem(KeyGenerator keyGenerator) {
    super();
    this.keyGenerator = keyGenerator;
    coercions.add(new StringCoercion());
    coercions.add(new PatternCoercion());
    coercions.add(new CollectionCoercion(coercions));
    coercions.add(new ValueOfMethodCoercion());
    coercions.add(new StringConstructorCoercion());
    log.info("Initialising configuration with key generator {} and coercions {}", keyGenerator, coercions);
  }

  public void registerField(Object target, Field field) {
    String key = keyGenerator.getKey(target, field);
    ConfiguredField configuredField = new ConfiguredField(key, target, field);
    fields.add(configuredField);

    configureField(configuredField);
  }

  public void add(ConfigurationSource source) {
    sources.add(source);
  }

  public void configure() {
    for (ConfiguredField field : fields) {
      configureField(field);
    }
  }

  protected void configureField(ConfiguredField field) {
    Coercion<?> coercion = getCoercion(field);
    String value = lookupValue(field.getKey());
    if (value != null)
      field.configure(coercion.coerce(field, value));
    else
      if (!field.hasDefaultValue())
        throw new ConfigurationValueNotFoundForKeyException(field.getKey(), sources);
  }

  private Coercion<?> getCoercion(CoercionTarget target) {
    for (Coercion<?> c : coercions) {
      if (c.isApplicableTo(target))
        return c;
    }

    throw new CoercionNotFoundForTypeException(target, coercions);
  }

  private String lookupValue(String key) {
    for (ConfigurationSource s : sources) {
      if (s.hasValue(key))
        return s.getValue(key);
    }

    return null;
  }

}
