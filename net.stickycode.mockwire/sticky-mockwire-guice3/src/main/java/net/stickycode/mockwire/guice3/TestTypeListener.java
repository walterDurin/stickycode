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
package net.stickycode.mockwire.guice3;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import net.stickycode.reflector.Reflector;

/**
 * Listen for the test type and define injectors for it.
 */
public class TestTypeListener
    implements TypeListener {

  private final Class<?> testType;

  public TestTypeListener(Class<?> testType) {
    super();
    this.testType = testType;
  }

  @Override
  public <I> void hear(TypeLiteral<I> type, final TypeEncounter<I> encounter) {
    Class<? super I> rawType = type.getRawType();
    if (rawType.equals(testType)) {
      final MethodFactoryDependencies valueCollector = new MethodFactoryDependencies(encounter);
      new Reflector()
          .forEachField(valueCollector)
          .process(rawType);

      encounter.register(new MockwireFieldInjector(valueCollector));
    }
  }
}
