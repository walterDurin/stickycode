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
package net.stickycode.ws.cxf;

import java.beans.Introspector;

public class WebServiceExposure {

  private Object implementation;

  private Class<?> contract;

  public WebServiceExposure(Object bean, Class<?> i) {
    this.implementation = bean;
    this.contract = i;
  }

  public Class<?> contract() {
    return contract;
  }

  public Object bean() {
    return implementation;
  }

  public String name() {
    return Introspector.decapitalize(implementation.getClass().getSimpleName());
  }

}