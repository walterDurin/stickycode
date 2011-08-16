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
package net.stickycode.stereotype.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.stickycode.stereotype.StickyComponent;

/**
 * This stereotype marks a {@link StickyComponent} as being a <a href="http://martinfowler.com/eaaCatalog/mapper.html">mapper</a>.
 *
 * It could be used to carry out context validation to ensure the pattern is being used appropriately.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@StickyComponent
public @interface StickyMapper {

}
