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
package net.stickycode.scheduled;

import net.stickycode.configured.ConfigurationAttribute;

/**
 * Contract for objects that can be run on a schedule. The scheduling system will invoke the run method as defined
 * by the {@link Schedule} returned by {@link #getSchedule()}.
 */
public interface ScheduledRunnable
    extends Runnable, ConfigurationAttribute {

  /**
   * Return the schedule with which the runnable should be executed. This is called once for each configuration cycle.
   *
   */
  Schedule getSchedule();

}
