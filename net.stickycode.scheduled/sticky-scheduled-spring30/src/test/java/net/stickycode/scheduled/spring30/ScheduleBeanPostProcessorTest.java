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
package net.stickycode.scheduled.spring30;

import static org.mockito.Mockito.verify;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfiguredConfiguration;
import net.stickycode.scheduled.ScheduledMethodInvoker;
import net.stickycode.scheduled.ScheduledRunnableRepository;
import net.stickycode.stereotype.Scheduled;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleBeanPostProcessorTest {

  public static class ScheduleTest {

    int counter = 0;

    @Scheduled
    public void runit() {
      counter++;
    }
  }
  
  @Mock
  ConfigurationRepository repository;
  
  @Mock
  ScheduledRunnableRepository schedulingSystem;
  
  @InjectMocks
  ScheduledBeanPostProcessor p = new ScheduledBeanPostProcessor();
  
  @Test
  public void scheduled() throws InterruptedException {
    ScheduleTest schedule = new ScheduleTest();
    p.postProcessAfterInstantiation(schedule, "schedule");
    
    verify(schedulingSystem).schedule(Matchers.any(ScheduledMethodInvoker.class));
    verify(repository).register(Matchers.any(ConfiguredConfiguration.class));
  }
}
