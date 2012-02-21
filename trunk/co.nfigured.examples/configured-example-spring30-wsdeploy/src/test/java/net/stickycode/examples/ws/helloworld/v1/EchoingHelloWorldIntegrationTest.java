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
package net.stickycode.examples.ws.helloworld.v1;

import static org.fest.assertions.Assertions.assertThat;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.Configured;

import org.junit.Test;
import org.junit.runner.RunWith;

import co.nfigured.example.helloworld.v1.HelloWorld;

@RunWith(MockwireRunner.class)
@MockwireConfigured({})
public class EchoingHelloWorldIntegrationTest {

  @Configured
  HelloWorld client;
  
  @Test
  public void ping() {
    assertThat(client).isNotNull();
    assertThat(client.hello("bob")).isEqualTo("Hello bob");
  }
  
}
