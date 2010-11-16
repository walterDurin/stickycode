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

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.StringCoercion;
import net.stickycode.coercion.StringConstructorCoercion;

public class ConfigurationSystem {

  private List<ConfiguredField> fields = new LinkedList<ConfiguredField>();
  private List<ConfigurationSource> sources = new LinkedList<ConfigurationSource>();
  private KeyGenerator keyGenerator = new SimpleNameDotFieldKeyGenerator();
  private List<Coercion<?>> coercions = new LinkedList<Coercion<?>>();

  public ConfigurationSystem() {
    super();
    coercions.add(new StringCoercion());
    coercions.add(new StringConstructorCoercion());
  }

  public void registerField(Object target, Field field) {
    String key = keyGenerator.getKey(target, field);
    fields.add(new ConfiguredField(key, target, field));
  }

  public void add(ConfigurationSource source) {
    sources.add(source);
  }

  public void configure() {
    for (ConfiguredField field : fields) {
      String value = lookupValue(field.getKey());
      Coercion<?> coercion = getCoercion(field);
      field.configure(coercion.coerce(field, value));
    }
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

    throw new ConfigurationValueNotFoundForKeyException(key, sources);
  }

}
