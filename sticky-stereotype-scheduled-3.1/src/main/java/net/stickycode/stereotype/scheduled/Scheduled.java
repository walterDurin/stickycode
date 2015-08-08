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
package net.stickycode.stereotype.scheduled;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Used to mark a field to be invoked on a schedule
 * </p>
 * 
 * <p>
 * The schedule is NOT defined on the method or annotation but in the configuration system of the context, this separation is
 * important
 * </p>
 * 
 * <p>
 * I would argue you can very rarely define the schedule of something when writing the code, scheduling is always environmental.
 * </p>
 * 
 * <p>
 * For example if I have a scheduled method that were to periodically synchronized two systems,
 * 
 * <ul>
 * <li>in a production environment this would most likely happen once a day</li>
 * <li>in a quality assurance testing suitable it should run far more regularly to be useful to testers and acceptors.</li>
 * <li>in development its is often desirable for these processes to not run or only run once</li>
 * </ul>
 * 
 * In order to ensure consistency across environments the schedule should always be defined by the environment, never by the code.
 * </p>
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scheduled {

  /**
   * Describe the scheduling such that when this message is presented to the user they can configure it
   * appropriately
   */
  String value() default "";
}
