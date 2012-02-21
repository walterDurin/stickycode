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
package net.stickycode.scheduled.configuration;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.stickycode.scheduled.PeriodicSchedule;
import net.stickycode.scheduled.Schedule;

public class PeriodicScheduleParser
    extends ScheduleParser {

  private Pattern periodic = Pattern.compile("every ([0-9]+)? ?([a-zA-Z]+)$");

  protected Pattern getPattern() {
    return periodic;
  }

  @Override
  public Schedule parse(Matcher match) {
    long period = parseNumber(match.group(1));
    TimeUnit periodUnit = parseTimeUnit(match.group(2));
    if (TimeUnit.NANOSECONDS.equals(periodUnit))
      throw new UnsupportedUnitForSchedulingException(periodUnit);

    return new PeriodicSchedule(period, periodUnit);
  }

}
