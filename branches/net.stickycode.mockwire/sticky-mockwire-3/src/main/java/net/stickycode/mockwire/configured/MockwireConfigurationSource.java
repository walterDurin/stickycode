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
package net.stickycode.mockwire.configured;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.stickycode.configured.ConfigurationSource;
import net.stickycode.mockwire.ClasspathResourceNotFoundException;


public class MockwireConfigurationSource
    implements ConfigurationSource {

  private Map<String, String> configuration = new HashMap<String, String>();

  @Override
  public boolean hasValue(String key) {
    return configuration.containsKey(key);
  }

  @Override
  public String getValue(String key) {
    return configuration.get(key);
  }

  public void add(Class<?> testClass, String s) {
    int index = s.indexOf('=');
    if (index > -1)
      addValue(s.substring(0, index), s.substring(index + 1));
    else
      loadClasspathResource(resolveBaseClass(testClass), s);
  }

  private Class<?> resolveBaseClass(Class<?> testClass) {
    if (testClass.isMemberClass())
      return testClass.getDeclaringClass();

    return testClass;
  }

  void loadClasspathResource(Class<?> testClass, String resource) {
    InputStream i = testClass.getResourceAsStream(resource);
    if (i == null)
      throw new ClasspathResourceNotFoundException(resource);

    Properties p = new Properties();
    try {
      p.load(i);
      i.close();
    }
    catch (IOException e) {
      throw new ClasspathResourceNotFoundException(e, resource);
    }

    for (String name : p.stringPropertyNames()) {
      addValue(name, p.getProperty(name));
    }
  }

  private void addValue(String key, String value) {
    configuration.put(key, value);
  }

  public void add(Class<?> testClass, String[] value) {
    for (String s : value) {
      add(testClass, s);
    }

  }

}