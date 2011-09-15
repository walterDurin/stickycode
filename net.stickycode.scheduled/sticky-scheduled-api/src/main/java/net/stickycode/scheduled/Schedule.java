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

import java.util.concurrent.TimeUnit;

/**
 * The definition of repetition for a {@link ScheduledRunnable} defining the cycle of execution and the alignment of that execution.
 */
public interface Schedule {

  /**
   * The delay to wait before the initial execution of the {@link ScheduledRunnable} to align the schedule as specified.
   */
  long getInitialDelay();

  /**
   * The time between executions of the {@link ScheduledRunnable}
   */
  long getPeriod();

  /**
   * The units to use when interpreting the Period and initial delay
   * @return the units of this schedule
   */
  TimeUnit getUnits();

  String toString();

}
