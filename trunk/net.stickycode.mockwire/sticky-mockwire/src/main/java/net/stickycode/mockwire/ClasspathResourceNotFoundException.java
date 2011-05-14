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
package net.stickycode.mockwire;

import java.io.IOException;

import net.stickycode.exception.PermanentException;


@SuppressWarnings("serial")
public class ClasspathResourceNotFoundException
    extends PermanentException {

  public ClasspathResourceNotFoundException(String resource) {
    super("Resource {} cound not be found in the classpath", resource);
  }

  public ClasspathResourceNotFoundException(Class<?> base, String resource) {
    super("Resource {} cound not be found in the classpath in package {}", resource, base.getPackage().getName());
  }

  public ClasspathResourceNotFoundException(IOException e, String resource) {
    super(e, "Resource {} cound not be found in the classpath", resource);
  }



}
