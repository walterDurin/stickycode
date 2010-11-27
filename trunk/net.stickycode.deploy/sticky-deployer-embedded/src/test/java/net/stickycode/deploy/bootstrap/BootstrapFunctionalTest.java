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
package net.stickycode.deploy.bootstrap;

import java.io.File;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class BootstrapFunctionalTest {

  @Test
  public void boot() throws ClassNotFoundException {
    StickyEmbedder b = new StickyEmbedder() {
      @Override
      protected File deriveApplicationFile() {
        return new File("target/sticky-deployer-embedded-sample.jar");
      }
    };

    assertThat(b.getLibraries()).hasSize(2);
    assertThat(b.getLibraries().iterator().next().getClasses()).hasSize(1);
    assertThat(b.getLibraries().iterator().next().getResources()).hasSize(4);
  }

}
