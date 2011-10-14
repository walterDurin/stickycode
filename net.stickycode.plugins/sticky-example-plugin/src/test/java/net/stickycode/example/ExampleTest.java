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
package net.stickycode.example;

import static org.fest.assertions.Assertions.assertThat;

import java.io.StringWriter;

import net.stickycode.resource.ClasspathResource;

import org.junit.Test;

public class ExampleTest {

  @Test
  public void helloworld() {
    StringWriter writer = new StringWriter();
    Example example = new Example();
    ClasspathResource resource = new ClasspathResource(getClass(), "helloworld.md");
    example.process(resource, writer);
    assertThat(writer.toString()).isEqualTo("<h1>Hello World!</h1>");
  }

  @Test
  public void codeblock() {
    StringWriter writer = new StringWriter();
    Example example = new Example();
    ClasspathResource resource = new ClasspathResource(getClass(), "codeblock.md");
    example.process(resource, writer);
    assertThat(writer.toString()).isEqualTo("<pre><code>some code\n</code></pre>");
  }
  
  @Test
  public void list() {
    StringWriter writer = new StringWriter();
    Example example = new Example();
    ClasspathResource resource = new ClasspathResource(getClass(), "list.md");
    example.process(resource, writer);
    assertThat(writer.toString()).isEqualTo("<pre><code>some code\n</code></pre>");
  }

  @Test
  public void externalCode() {
    StringWriter writer = new StringWriter();
    Example example = new Example();
    ClasspathResource resource = new ClasspathResource(getClass(), "externalcode.md");
    example.process(resource, writer);
    String tostring = writer.toString();
    assertThat(tostring).isEqualTo("<pre><code>some code\n</code></pre>");
  }

  @Test
  public void example2() {
   assertThat(new Example().process("@Inject")).isEqualTo("<a href=\"#\" id=\"see-Inject\">@Inject</a>");
  }

}
