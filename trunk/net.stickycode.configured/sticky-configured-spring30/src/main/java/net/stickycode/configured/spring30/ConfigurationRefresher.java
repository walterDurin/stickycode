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
package net.stickycode.configured.spring30;

import javax.inject.Inject;

import net.stickycode.configured.ConfigurationSystem;
import net.stickycode.stereotype.StickyComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@StickyComponent
public class ConfigurationRefresher
    implements ApplicationListener<ContextRefreshedEvent> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  public ConfigurationSystem system;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("Configure system on event {}", event);
    system.start();
  }

}
