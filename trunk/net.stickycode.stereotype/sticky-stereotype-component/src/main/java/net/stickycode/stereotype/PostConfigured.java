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
package net.stickycode.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Metadata to mark a method to be invoked after the configured fields are set.
 * </p>
 *
 * <h3>Examples</h3>
 * <p>
 * Using classpath scanning with spring for example you would get a component <b>themeService</b> which will be initialised with
 * values from keys <b>themeService.defaultTheme</b>. beforeConfiguration will be invoked before defaultTheme is set while
 * afterConfiguration will be invoked after defaultTheme is set.
 * </p>
 *
 * <pre>
 * &#064;StickyComponent
 * public ThemeService {
 *
 *   &#064;Configured
 *   private String defaultTheme;
 *
 *   &#064;PreConfigured
 *   public void beforeConfiguration() {
 *    assertThat(defaultTheme).isNull();
 *   }
 *
 *   &#064;PostConfigured
 *   public void afterConfiguration() {
 *    assertThat(defaultTheme).isNotNull();
 *   }
 * }
 * </pre>
 *
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostConfigured {

}
